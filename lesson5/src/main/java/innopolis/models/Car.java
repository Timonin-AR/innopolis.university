package innopolis.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Integer id, clientId;
    private String name, model, number;
}
