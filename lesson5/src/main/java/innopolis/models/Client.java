package innopolis.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Integer id, phoneNumber;
    private String firsName, midlName, lastName;
    private LocalDate dateOfBirth;
}
