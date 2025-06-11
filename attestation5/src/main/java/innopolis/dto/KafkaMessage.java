package innopolis.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class KafkaMessage {

    private Long id;
    private String message;
    private LocalDateTime sendTime;
}
