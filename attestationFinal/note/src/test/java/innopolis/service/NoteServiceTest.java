package innopolis.service;

import innopolis.model.ActionEventEnum;
import innopolis.model.NoteDto;
import innopolis.model.NoteEntity;
import innopolis.model.NoteEventDto;
import innopolis.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;


    @InjectMocks
    private NoteService noteService;

    @Test
    void createNote_ShouldReturnNoteDto_WhenDateIsValid() {
        // Given
        var userId = 1L;
        var inputNote = NoteDto.builder()
                .title("Test Title")
                .description("Test Description")
                .createDate(LocalDate.now())
                .build();

        var savedEntity = NoteEntity.builder()
                .id(1L)
                .userId(userId)
                .title(inputNote.getTitle())
                .description(inputNote.getDescription())
                .createDate(inputNote.getCreateDate())
                .isActual(true)
                .isArchive(false)
                .build();

        when(noteRepository.save(any(NoteEntity.class))).thenReturn(savedEntity);


        // When
        var result = noteService.createNote(inputNote, userId);

        // Then
        assertNotNull(result);
        assertEquals(savedEntity.getId(), result.getNoteId());
        assertEquals(savedEntity.getTitle(), result.getTitle());
        verify(noteRepository, times(1)).save(any(NoteEntity.class));

    }

    @Test
    void createNote_ShouldThrowException_WhenDateIsInvalid() {
        // Given
        var inputNote = NoteDto.builder()
                .title("Test Title")
                .description("Test Description")
                .createDate(LocalDate.now().minusDays(1))  // Прошедшая дата
                .build();

        // When & Then
        assertThrows(RuntimeException.class, () -> noteService.createNote(inputNote, 1L));
        verify(noteRepository, never()).save(any());
    }

    @Test
    void updateNote_ShouldReturnUpdatedNoteDto_WhenNoteExistsAndUserIsOwner() {
        // Given
        var userId = 1L;
        var noteId = 1L;
        var inputNote = NoteDto.builder()
                .noteId(noteId)
                .title("Updated Title")
                .description("Updated Description")
                .build();

        var existingNote = NoteEntity.builder()
                .id(noteId)
                .userId(userId)
                .title("Old Title")
                .description("Old Description")
                .createDate(LocalDate.now())
                .build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(existingNote)).thenReturn(existingNote);


        // When
        var result = noteService.updateNote(inputNote, userId);

        // Then
        assertEquals(inputNote.getTitle(), result.getTitle());
        assertEquals(inputNote.getDescription(), result.getDescription());
        verify(noteRepository, times(1)).save(existingNote);
    }

    @Test
    void updateNote_ShouldThrowException_WhenUserIsNotOwner() {
        // Given
        var ownerId = 1L;
        var anotherUserId = 2L;
        var noteId = 1L;

        var inputNote = NoteDto.builder().noteId(noteId).build();
        var existingNote = NoteEntity.builder().id(noteId).userId(ownerId).build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));

        // When & Then
        assertThrows(RuntimeException.class, () -> noteService.updateNote(inputNote, anotherUserId));
        verify(noteRepository, never()).save(any());
    }

    @Test
    void getNotes_ShouldReturnListFromRepository_WhenCacheIsEmpty() {
        // Given
        Long userId = 1L;
        List<NoteEntity> mockNotes = List.of(
                NoteEntity.builder().id(1L).userId(userId).title("Note 1").build(),
                NoteEntity.builder().id(2L).userId(userId).title("Note 2").build()
        );

        when(noteRepository.findAllActualNotesByUserId(userId)).thenReturn(mockNotes);


        // When
        List<NoteDto> result = noteService.getNotes(userId);

        // Then
        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findAllActualNotesByUserId(userId);
    }

    @Test
    void deleteNoteById_ShouldDeleteNote_WhenUserIsOwner() {

        Long userId = 1L;
        Long noteId = 1L;
        NoteDto inputNote = NoteDto.builder().noteId(noteId).build();

        NoteEntity existingNote = NoteEntity.builder()
                .id(noteId)
                .userId(userId)
                .build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));

        noteService.deleteNoteById(inputNote, userId);

        verify(noteRepository, times(1)).deleteById(noteId);

    }

    @Test
    void getArchiveNotes_ShouldReturnArchiveNotesForUser() {
        // Given
        Long userId = 1L;
        List<NoteEntity> mockNotes = List.of(
                NoteEntity.builder()
                        .id(1L)
                        .userId(userId)
                        .title("Archived Note 1")
                        .isArchive(true)
                        .build(),
                NoteEntity.builder()
                        .id(2L)
                        .userId(userId)
                        .title("Archived Note 2")
                        .isArchive(true)
                        .build()
        );

        when(noteRepository.findAllArchiveNotesByUserId(userId)).thenReturn(mockNotes);

        // When
        List<NoteDto> result = noteService.getArchiveNotes(userId);

        // Then
        assertEquals(2, result.size());
        assertEquals("Archived Note 1", result.get(0).getTitle());
        verify(noteRepository, times(1)).findAllArchiveNotesByUserId(userId);
    }

    @Test
    void startEventDeleteArchiveNotes_ShouldSendDeleteEventForArchiveNotes() {
        // Given
        List<Long> noteIds = List.of(1L, 2L);
        when(noteRepository.findAllArchiveNotes()).thenReturn(
                List.of(
                        NoteEntity.builder().id(1L).build(),
                        NoteEntity.builder().id(2L).build()
                )
        );

        // When
        noteService.startEventDeleteArchiveNotes();

        // Then
        verify(noteRepository, times(1)).findAllArchiveNotes();
        verify(kafkaProducerService, times(1)).sendEventWithNoteIds(new NoteEventDto(noteIds, ActionEventEnum.DELETE));
    }

    @Test
    void startEventActualNotes_ShouldSendActiveEventForTodayNotes() {
        // Given
        LocalDate today = LocalDate.now();
        List<Long> noteIds = List.of(1L);

        when(noteRepository.findAllNotesByCreateDate(today)).thenReturn(
                List.of(NoteEntity.builder().id(1L).build())
        );


        // When
        noteService.startEventActualNotes();

        // Then
        verify(noteRepository, times(1)).findAllNotesByCreateDate(today);
        verify(kafkaProducerService, times(1)).sendEventWithNoteIds(new NoteEventDto(noteIds, ActionEventEnum.ACTIVE));

    }

    @Test
    void startEventArchiveNotes_ShouldSendArchiveEventForYesterdayNotes() {
        // Given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Long> noteIds = List.of(1L);

        when(noteRepository.findAllNotesByCreateDate(yesterday)).thenReturn(
                List.of(NoteEntity.builder().id(1L).build())
        );

        // When
        noteService.startEventArchiveNotes();

        // Then
        verify(noteRepository, times(1)).findAllNotesByCreateDate(yesterday);
        verify(kafkaProducerService, times(1)).sendEventWithNoteIds(new NoteEventDto(noteIds, ActionEventEnum.ARCHIVE));
    }
}
