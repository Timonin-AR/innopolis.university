package innopolis.controller;

import innopolis.entity.Note;
import innopolis.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{id}")
    public Note getNote(@PathVariable Integer id) {
        return noteService.getNoteById(id);
    }

    @PostMapping()
    public Integer createNote(Note note) {
        return noteService.createNote(note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Integer id) {
        noteService.deleteNote(id);
    }

    @PutMapping()
    public void updateNote(@RequestBody Note note) {
        noteService.updateNote(note);
    }
}
