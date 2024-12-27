package innopolis.entity;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientXCar {
    private Integer id;
    private Integer carId;
    private Integer clientId;
}
