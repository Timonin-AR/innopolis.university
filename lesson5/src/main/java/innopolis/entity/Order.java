package innopolis.entity;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id, carId, clientId, costOfWork;
}
