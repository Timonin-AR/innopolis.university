package innopolis.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.dto.KafkaMessage;
import innopolis.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final ReactiveKafkaProducerTemplate<String, KafkaMessage> reactiveKafkaProducerTemplate;

    private final KafkaConsumer consumer;

    private final ObjectMapper mapper;

    public Flux<SenderResult<Void>> sendMessage(OrderEntity order) {
        return Flux.just(order).map(integer -> {
            KafkaMessage kafkaMessage = null;
            try {
                kafkaMessage = KafkaMessage.builder()
                        .id(order.getId())
                        .message(mapper.writeValueAsString(order))
                        .sendTime(LocalDateTime.now())
                        .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return MessageBuilder.withPayload(kafkaMessage)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

        }).flatMap(kafkaMessage -> {
            log.info("Отправка в кафку заказа: {}", kafkaMessage.getPayload().getMessage());
            return reactiveKafkaProducerTemplate.send("order", kafkaMessage);

        });
    }

}
