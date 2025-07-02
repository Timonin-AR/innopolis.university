package innopolis.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "userid")
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(name = "isactual")
    private Boolean isActual;

    @Column(name = "isarchive")
    private Boolean isArchive;

    @Column(name = "createdate")
    private LocalDate createDate;


}
