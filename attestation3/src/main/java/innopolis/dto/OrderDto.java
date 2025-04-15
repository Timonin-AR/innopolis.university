package innopolis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private String number;
    private String carName;
    private String fio;
    private Long price;
    private String details;
}
