package innopolis.handler;

import innopolis.entity.OrderEntity;
import innopolis.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KafkaHandler {

    private final KafkaProducer kafkaProducer;

    public Mono<ServerResponse> sendMessageToKafka(OrderEntity serverRequest) {
        return kafkaProducer
                .sendMessage(serverRequest)
                .then()
                .flatMap(message -> ServerResponse
                        .ok()
                        .body(Mono.just("Отправлено сообщение"), String.class));
    }

}
