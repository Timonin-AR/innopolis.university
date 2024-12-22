package innopolis.entity;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Integer id;
    private Integer clientId;
    private String name;
    private String model;
    private String number;
}
