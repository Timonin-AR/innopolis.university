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
    @NonNull
    private String phoneNumber;
    @NonNull
    private String firsName;
    @NonNull
    private String midlName;
    @NonNull
    private String lastName;
    private Timestamp dateOfBirth;
}
