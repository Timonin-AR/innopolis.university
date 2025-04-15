package innopolis.dto;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ClientDto {
    private String fio;
    private String phone;
    private Timestamp dateOfBirth;
}
