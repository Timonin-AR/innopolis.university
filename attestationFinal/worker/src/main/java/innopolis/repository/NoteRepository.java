package innopolis.repository;

import innopolis.model.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            update schemanotes.notes
            set isactual = true, isarchive = false
            where id in (:ids)
            """)
    void updateColumnIsActualLikeTrue(@Param("ids") List<Long> noteIds);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            update schemanotes.notes
            set isarchive = true, isactual = false
            where id in (:ids)
            """)
    void updateColumnIsArchiveLikeTrue(@Param("ids") List<Long> noteIds);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            DELETE FROM schemanotes.notes
            WHERE id IN (:ids)
            """)
    void deleteArchiveNotes(@Param("ids") List<Long> noteIds);
}
