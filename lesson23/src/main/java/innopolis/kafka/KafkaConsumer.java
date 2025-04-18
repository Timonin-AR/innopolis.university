package innopolis.kafka;

import innopolis.dto.KafkaMessage;
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

    private final ReactiveKafkaConsumerTemplate<String, KafkaMessage> consumerTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void processMessage() {
        consumerTemplate
                .receiveAutoAck()
                .doOnNext(mesage -> {
                    KafkaMessage messageFromTopic = mesage.value();
                    log.info("Прочитано сообщение: {} {}", messageFromTopic.getMessage(), messageFromTopic.getSendTime());
                })
                .doOnComplete(() -> log.info("Чтение окончено "))
                .subscribe();
    }


//    @KafkaListener(topics = "message", groupId = "group")
//    public void listen(KafkaMessage message) {
//        log.info("Прочитано сообщение: {}", message);
//    }
}
