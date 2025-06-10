package innopolis.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Класс для работы с сущностями клиент из базы данных
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
@Schema(description = "Сущность для работы с базой данных по клиентам")
public class ClientEntity {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String fio;

    @Column
    private String phone;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    @Column(name = "is_delete")
    private Boolean isDelete;
}
