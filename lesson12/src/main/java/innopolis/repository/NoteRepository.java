package innopolis.repository;

import innopolis.entity.Note;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepository implements IRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer create(Note note) {
        return jdbcTemplate.query("""
                        insert into my.note (topic, text, dateAndTime)
                        values(?, ?, current_timestamp) RETURNING noteId;
                        """,
                (result, row) -> result.getInt(1),
                note.getTopic(), note.getText()).stream().findFirst().orElseThrow();
    }

    @Override
    public Note read(Integer noteId) {
        return jdbcTemplate.queryForObject("select * from my.note mn where mn.noteId = ?", new BeanPropertyRowMapper<>(Note.class), noteId);
    }

    @Override
    public void update(Note note) {
        jdbcTemplate.update("update my.note mn set topic=?, text=?, dateandtime=current_timestamp where mn.noteid = ?", note.getTopic(), note.getText(), note.getNoteId());

    }

    @Override
    public void delete(Integer noteId) {
        jdbcTemplate.update("delete from my.note mn where mn.noteid = ?", noteId);
    }
}
