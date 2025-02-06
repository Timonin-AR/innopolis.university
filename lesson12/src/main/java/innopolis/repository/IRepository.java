package innopolis.repository;

import innopolis.entity.Note;

public interface IRepository {

    Integer create(Note note);
    Note read(Integer noteId);
    void update(Note note);
    void delete(Integer noteId);


}
