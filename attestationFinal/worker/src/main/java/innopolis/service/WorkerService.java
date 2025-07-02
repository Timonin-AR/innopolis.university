package innopolis.service;

import innopolis.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService {
    private final NoteRepository noteRepository;

    public void sendNoteToArchive(List<Long> noteIds) {
        noteRepository.updateColumnIsArchiveLikeTrue(noteIds);
        log.info("Заметки {} были отмечены как архивные", noteIds);
    }

    public void sendNoteToActual(List<Long> noteIds){
        noteRepository.updateColumnIsActualLikeTrue(noteIds);
        log.info("Заметки {} были отмечены как актуальные", noteIds);
    }

    public void deleteArchiveNote(List<Long> noteIds){
        noteRepository.deleteArchiveNotes(noteIds);
        log.info("Заметки {} были удалены", noteIds);
    }
}
