package innopolis.controller;

import innopolis.aspect.JwtAuth;
import innopolis.model.NoteDto;
import innopolis.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @JwtAuth
    @PostMapping("/create")
    @Operation(summary = "Метод для добавления новой заметки",
            description = """
                    Если создавать заметку без даты, то будет проставлена дата сегодняшняя и она будет иметь статус "Актуальная".
                    Если указать планируемую дату, к примеру завтра, то заметка станет "Актуальной" именно в указанную дату.
                    Заметки имеют статус "Актуальная" один день, а после меняют статус на "Архивная".
                    Архивные заявки автоматически удаляются через 6 месяцев от даты создания.
                    Дату создания можно обновить.
                    """)
    public ResponseEntity<Long> create(@RequestBody NoteDto noteDto, HttpServletRequest request) {
        return ResponseEntity.ok(noteService.createNote(noteDto, getUserIdFromAttribute(request)).getNoteId());
    }

    @JwtAuth
    @PostMapping("/update")
    @Operation(summary = "Метод для обновления заметки")
    public ResponseEntity<NoteDto> update(@RequestBody NoteDto noteDto, HttpServletRequest request) {
        return ResponseEntity.ok(noteService.updateNote(noteDto, getUserIdFromAttribute(request)));
    }

    @JwtAuth
    @GetMapping("/all")
    @Operation(summary = "Метод для получения всех актуальных заметок",
            description = """
                    Метод возвращает список актуальных заметок, которые имеют статус "Актуальная".
                    Этот список будет меняться т.к заметки имеют статус "Актуальная" один день от даты создания.
                    """)
    public ResponseEntity<List<NoteDto>> getAllNotes(@RequestBody NoteDto noteDto, HttpServletRequest request) {
        return ResponseEntity.ok(noteService.getNotes(getUserIdFromAttribute(request)));
    }

    @JwtAuth
    @GetMapping("/archive")
    @Operation(summary = "Метод для получения всех архивных заметок",
            description = """
                    Метод возвращает список архивных заметок, которые имеют статус "Архивная".
                    Этот список будет меняться т.к архивные заявки автоматически удаляются через 6 месяцев от даты создания.
                    """)
    public ResponseEntity<List<NoteDto>> getAllArchiveNotes(@RequestBody NoteDto noteDto, HttpServletRequest request) {
        return ResponseEntity.ok(noteService.getArchiveNotes(getUserIdFromAttribute(request)));
    }

    @JwtAuth
    @DeleteMapping("/delete")
    @Operation(summary = "Метод для удаления заметки", description = "Метод безвозвратно удаляет указанную заметку")
    public ResponseEntity<String> delete(@RequestBody NoteDto noteDto, HttpServletRequest request) {
        noteService.deleteNoteById(noteDto, getUserIdFromAttribute(request));
        return ResponseEntity.ok("Удалена заметка " + noteDto.getNoteId());
    }

    /**
     * Метод достает userId из атрибута, который подставляется аспектом см {@link JwtAuth}
     * @param request Запрос пользователя
     * @return возвращает userId
     */
    private static Long getUserIdFromAttribute(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }
}
