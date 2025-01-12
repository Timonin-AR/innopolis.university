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
    @NonNull
    private Integer carId;
    @NonNull
    private Integer clientId;
}
