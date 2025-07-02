package innopolis.service;

import innopolis.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WorkerServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private Logger log;

    @InjectMocks
    private WorkerService workerService;

    @Test
    void sendNoteToArchive_ShouldCallRepositoryAndLog() {
        List<Long> noteIds = List.of(1L, 2L, 3L);
        workerService.sendNoteToArchive(noteIds);
        verify(noteRepository, times(1)).updateColumnIsArchiveLikeTrue(noteIds);
    }

    @Test
    void sendNoteToActual_ShouldCallRepositoryAndLog() {
        List<Long> noteIds = List.of(4L, 5L);
        workerService.sendNoteToActual(noteIds);
        verify(noteRepository, times(1)).updateColumnIsActualLikeTrue(noteIds);

    }

    @Test
    void deleteArchiveNote_ShouldCallRepositoryAndLog() {
        List<Long> noteIds = List.of(6L, 7L, 8L, 9L);
        workerService.deleteArchiveNote(noteIds);
        verify(noteRepository, times(1)).deleteArchiveNotes(noteIds);

    }

    @Test
    void sendNoteToArchive_ShouldHandleEmptyList() {
        List<Long> emptyList = List.of();
        workerService.sendNoteToArchive(emptyList);
        verify(noteRepository, times(1)).updateColumnIsArchiveLikeTrue(emptyList);
    }


}
