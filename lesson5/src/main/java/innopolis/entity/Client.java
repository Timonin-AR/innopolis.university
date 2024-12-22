package innopolis.entity;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Integer id;
    private Integer phoneNumber;
    private String firsName;
    private String midlName;
    private String lastName;
    private Timestamp dateOfBirth;
}
