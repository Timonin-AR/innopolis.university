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
    private Integer id, phoneNumber;
    private String firsName, midlName, lastName;
    private Timestamp dateOfBirth;
}
