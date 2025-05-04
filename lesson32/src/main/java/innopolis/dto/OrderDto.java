package innopolis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Класс для отображения информации для пользователей о заказах
 */

@Data
@Builder
public class OrderDto {
    @Schema(description = "Номер машины")
    private String number;
    @Schema(description = "Марка и модель машины")
    private String carName;
    @Schema(description = "ФИО владельца машины")
    private String fio;
    @Schema(description = "Итоговая стоимость заказа")
    private Long price;
    @Schema(description = "Информация о деталях заказа")
    private String details;
}
