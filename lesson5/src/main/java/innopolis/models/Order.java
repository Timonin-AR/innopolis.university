package innopolis.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id, carId, clientId, costOfWork;
}
