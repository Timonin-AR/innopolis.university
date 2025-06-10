package innopolis.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;


/**
 * Класс для работы с сущностями заказ из базы данных
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"order\"")
@Schema(description = "Сущность для работы с базой данных по заказам")
public class OrderEntity {
    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Column
    private Long price;

    @Column
    private String details;

}
