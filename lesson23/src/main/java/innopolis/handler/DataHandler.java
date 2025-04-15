package innopolis.handler;

import innopolis.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DataHandler {

    private final KafkaProducer kafkaProducer;

    public Mono<ServerResponse> sendMessageToKafka(ServerRequest serverRequest) {
        var count = Integer.valueOf(serverRequest.queryParam("count").orElse("10"));

        return kafkaProducer
                .sendMessage(count)
                .then()
                .flatMap(message -> ServerResponse
                        .ok()
                        .body(Mono.just("Отправлено сообщение"), String.class));
    }

//    public Mono<ServerResponse> getHello(ServerRequest serverRequest) {
//        return ServerResponse.ok().body(Mono.just("hello"), String.class);
//    }
}
