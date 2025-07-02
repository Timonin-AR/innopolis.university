package innopolis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes", schema = "schemanotes")
public class NoteEntity {
    @Id
    private Long id;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "isarchive")
    private Boolean isArchive;
}
