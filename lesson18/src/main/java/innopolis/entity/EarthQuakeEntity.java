package innopolis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "earthquake")
public class EarthQuakeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private LocalDateTime time;
    private Double mag;
    private String place;
}
