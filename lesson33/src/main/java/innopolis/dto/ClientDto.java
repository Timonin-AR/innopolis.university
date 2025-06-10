package innopolis.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Класс для отображения информации для пользователей о клиентах
 */
@Data
@Builder
public class ClientDto {
    @Schema(description = "Фамилия Имя Отчество")
    private String fio;
    @Schema(description = "Телефон")
    private String phone;
    @Schema(description = "Дата рождения")
    private Timestamp dateOfBirth;
}
