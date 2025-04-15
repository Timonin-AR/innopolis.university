package innopolis.service;

import innopolis.entity.Note;
import innopolis.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Integer createNote(Note note) {
       return noteRepository.create(note);
    }

    public Note getNoteById(Integer noteId) {
        return noteRepository.read(noteId);
    }

    public void deleteNote(Integer noteId) {
        noteRepository.delete(noteId);
    }

    public void updateNote(Note note) {
        noteRepository.update(note);
    }


}
