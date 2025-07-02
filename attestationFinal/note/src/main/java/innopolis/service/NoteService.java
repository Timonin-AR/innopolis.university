package innopolis.service;

import innopolis.model.ActionEventEnum;
import innopolis.model.NoteDto;
import innopolis.model.NoteEntity;
import innopolis.model.NoteEventDto;
import innopolis.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final KafkaProducerService kafkaProducerService;

    /**
     * Метод создания новой заметки
     *
     * @param noteDto объект заметки, из которой будут взяты данные для создания
     * @param userId  идентификатор пользователя, которому принадлежит заметка
     * @return возвращает созданный объект в базе данных
     */
    @CacheEvict(cacheNames = "notes", key = "#userId")
    public NoteDto createNote(NoteDto noteDto, Long userId) {
        var dateNow = LocalDate.now();
        var dateCreate = noteDto.getCreateDate() == null ? dateNow : noteDto.getCreateDate();

        if (!dateCreate.isBefore(dateNow)) {
            var newNote = noteRepository.save(NoteEntity.builder()
                    .userId(userId)
                    .title(noteDto.getTitle())
                    .description(noteDto.getDescription())
                    .createDate(dateCreate)
                    .isActual(dateCreate.equals(dateNow))
                    .isArchive(false)
                    .build());

            return NoteDto.builder()
                    .noteId(newNote.getId())
                    .title(newNote.getTitle())
                    .description(newNote.getDescription())
                    .createDate(newNote.getCreateDate())
                    .build();
        } else {
            throw new RuntimeException("Дата должна быть равна или больше сегодняшнего числа");
        }
    }

    /**
     * Метод обновляет заявку
     *
     * @param noteDto объект заметки, из которой будут взяты данные по обновлению
     * @param userId  идентификатор пользователя, которому принадлежит заметка
     * @return возвращает обновленный объект {@link NoteDto}
     */
    @CacheEvict(cacheNames = "notes", key = "#userId")
    public NoteDto updateNote(NoteDto noteDto, Long userId) {
        var updatedNote = getNotesOwner(noteDto, userId);
        updatedNote.setTitle(noteDto.getTitle());
        updatedNote.setDescription(noteDto.getDescription());
        updatedNote.setCreateDate(noteDto.getCreateDate() != null ? noteDto.getCreateDate() : updatedNote.getCreateDate());

        var newNote = noteRepository.save(updatedNote);
        return NoteDto.builder()
                .noteId(newNote.getId())
                .title(newNote.getTitle())
                .description(newNote.getDescription())
                .createDate(newNote.getCreateDate())
                .build();
    }

    /**
     * Метод возвращает только "Актуальные" заметки пользователя
     *
     * @param userId идентификатор пользователя, которому принадлежат заметки
     * @return возвращает список объектов {@link NoteDto}
     */

    @Cacheable(cacheNames = "notes", key = "#userId")
    public List<NoteDto> getNotes(Long userId) {
        return noteRepository.findAllActualNotesByUserId(userId)
                .stream()
                .map(noteEntity -> NoteDto.builder()
                        .noteId(noteEntity.getId())
                        .title(noteEntity.getTitle())
                        .description(noteEntity.getDescription())
                        .createDate(noteEntity.getCreateDate())
                        .build())
                .toList();
    }

    /**
     * Метод возвращает только "Архивные" заметки пользователя
     *
     * @param userId идентификатор пользователя, которому принадлежат заметки
     * @return возвращает список объектов {@link NoteDto}
     */
    public List<NoteDto> getArchiveNotes(Long userId) {
        return noteRepository.findAllArchiveNotesByUserId(userId)
                .stream()
                .map(noteEntity -> NoteDto.builder()
                        .noteId(noteEntity.getId())
                        .title(noteEntity.getTitle())
                        .description(noteEntity.getDescription())
                        .createDate(noteEntity.getCreateDate())
                        .build())
                .toList();
    }

    @CacheEvict(cacheNames = "notes", key = "#userId")
    public void deleteNoteById(NoteDto noteId, Long userId) {
        noteRepository.deleteById(getNotesOwner(noteId, userId).getId());
    }

    @Scheduled(cron = "0 * * * * *")  // Каждый день в 3:00
    public void startEventDeleteArchiveNotes() {
        var oldNoteIds = noteRepository.findAllArchiveNotes().stream().map(NoteEntity::getId).toList();
        kafkaProducerService.sendEventWithNoteIds(new NoteEventDto(oldNoteIds, ActionEventEnum.DELETE));
        log.info("Стартовала задача по удалению архивных заметок");
    }

    @CacheEvict(cacheNames = "notes", allEntries = true)
    @Scheduled(cron = "0 * * * * *")  // Полночь каждый день
    public void startEventActualNotes() {
        var oldNoteIds = noteRepository.findAllNotesByCreateDate(LocalDate.now()).stream().filter(note -> !note.getIsActual()).map(NoteEntity::getId).toList();
        kafkaProducerService.sendEventWithNoteIds(new NoteEventDto(oldNoteIds, ActionEventEnum.ACTIVE));
        log.info("Стартовала задача по обновлению актуальных заметок");
    }

    @Scheduled(cron = "0 * * * * *")  // Каждый день в 1:00
    public void startEventArchiveNotes() {
        var oldNoteIds = noteRepository.findAllNotesByCreateDate(LocalDate.now().minusDays(1)).stream().map(NoteEntity::getId).toList();
        kafkaProducerService.sendEventWithNoteIds(new NoteEventDto(oldNoteIds, ActionEventEnum.ARCHIVE));
        log.info("Стартовала задача по обновлению архивных заметок");
    }

    /**
     * Метод находит заметку по id и проверят, что она принадлежит запрашиваемым пользователем
     *
     * @param noteDto объект заметки, из которого будет взять id
     * @param userId  идентификатор пользователя, которому принадлежит заметка
     * @return возвращает полученный объект из базы данных типа {@link NoteEntity}
     */
    private NoteEntity getNotesOwner(NoteDto noteDto, Long userId) {
        var noteId = noteDto.getNoteId();
        var noteEntity = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Заметка не найдена под id" + noteId));
        if (noteEntity.getUserId().equals(userId)) {
            return noteEntity;
        } else {
            throw new RuntimeException("Нельзя взаимодействовать c чужими заметками");
        }
    }

}
