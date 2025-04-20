package innopolis.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
public class CarEntity {
    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String number;
}
