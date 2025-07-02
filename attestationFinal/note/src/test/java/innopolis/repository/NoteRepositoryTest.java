package innopolis.repository;

import innopolis.model.NoteEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class NoteRepositoryTest {
    private final LocalDate now = LocalDate.now();
    private final NoteEntity testNote = NoteEntity.builder()
            .userId(1L)
            .title("Тест заметка")
            .description("Тестовое описание")
            .createDate(now)
            .isArchive(false)
            .isActual(true).build();

    @Autowired
    public NoteRepository noteRepository;

    @Test
    void saveNoteTest() {
        assertThat(noteRepository.save(testNote).getId()).isGreaterThan(0);
    }

    @Test
    void findAllActualNotesTest() {
        noteRepository.save(testNote);
        assertThat(noteRepository.findAllActualNotesByUserId(1L).stream().findFirst().orElseThrow().getIsActual()).isTrue();
    }

    @Test
    void findAllArchiveNotesByUserTest() {
        testNote.setIsArchive(true);
        testNote.setIsActual(false);
        noteRepository.save(testNote);
        assertThat(noteRepository.findAllArchiveNotesByUserId(1L).stream().findFirst().orElseThrow().getIsArchive()).isTrue();
    }

    @Test
    void findAllArchiveNotesTest() {
        testNote.setIsArchive(true);
        testNote.setIsActual(false);
        noteRepository.save(testNote);
        assertThat(noteRepository.findAllArchiveNotes().stream().findFirst().orElseThrow().getIsArchive()).isTrue();
    }

    @Test
    void findAllNoteByCreateDateTest() {
        noteRepository.save(testNote);
        assertThat(noteRepository.findAllNotesByCreateDate(now).stream().findFirst().orElseThrow().getCreateDate()).isEqualTo(now);
    }


}
