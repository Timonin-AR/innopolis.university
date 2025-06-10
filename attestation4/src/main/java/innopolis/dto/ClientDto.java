package innopolis.dto;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Класс для отображения информации для пользователей о клиентах
 */
@Data
@Builder
public class ClientDto {
    private String fio;
    private String phone;
    private Timestamp dateOfBirth;
}
