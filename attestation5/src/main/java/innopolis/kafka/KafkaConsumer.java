package innopolis.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.dto.KafkaMessage;
import innopolis.entity.OrderEntity;
import innopolis.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final OrderService orderService;
    private final ReactiveKafkaConsumerTemplate<String, KafkaMessage> consumerTemplate;
    private final ObjectMapper mapper;

    @EventListener(ApplicationReadyEvent.class)
    public void processMessage() {
        log.info("Вычитываю сообщение");
        consumerTemplate
                .receiveAutoAck()
                .doOnNext(message -> {
                    try {
                        orderService.saveOrder(mapper.readValue(message.value().getMessage(), OrderEntity.class));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    KafkaMessage messageFromTopic = message.value();
                    log.info("Прочитано сообщение: {} {}", messageFromTopic.getMessage(), messageFromTopic.getSendTime());
                })
                .doOnComplete(() -> log.info("Чтение окончено "))
                .subscribe();
    }

}
