package innopolis;

import innopolis.entity.Note;
import innopolis.repository.NoteRepository;
import innopolis.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void createNoteTest() {
        var note = new Note(null, "Тема", "Текст", null);
        when(noteRepository.create(note)).thenReturn(1);
        assertThat(noteService.createNote(note)).isEqualTo(1);

    }

    @Test
    void getNoteByIdTest() {
        var note = new Note(1, "Тема", "Текст", Timestamp.valueOf(LocalDateTime.now()));
        when(noteRepository.read(1)).thenReturn(note);
        assertThat(noteService.getNoteById(1)).extracting(Note::getNoteId, Note::getTopic, Note::getText, Note::getDateAndTime).isNotNull();
    }

    @Test
    void deleteNoteTest() {
        noteService.deleteNote(1);
    }

    @Test
    void updateNoteTest() {
        var note = new Note(null, "Тема", "Текст", null);
        noteService.updateNote(note);
    }


}
