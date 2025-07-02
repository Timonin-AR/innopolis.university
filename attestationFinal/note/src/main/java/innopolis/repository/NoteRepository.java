package innopolis.repository;

import innopolis.model.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM schemanotes.notes n
            where n.userid = ? and n.isactual = true
            """)
    List<NoteEntity> findAllActualNotesByUserId(Long userId);

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM schemanotes.notes n
            where n.userid = ? and n.isarchive = true
            """)
    List<NoteEntity> findAllArchiveNotesByUserId(Long userId);


    @Query(nativeQuery = true, value = """
            SELECT *
            FROM schemanotes.notes n
            where n.isarchive = true
            """)
    List<NoteEntity> findAllArchiveNotes();

    @Query(nativeQuery = true, value = """
            select *
            from schemanotes.notes n
            where n.createdate = ?
            """)
    List<NoteEntity> findAllNotesByCreateDate(LocalDate date);
}
