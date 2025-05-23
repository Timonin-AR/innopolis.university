package innopolis.controller;

import innopolis.handler.DataHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataRouteController {

    private final DataHandler dataHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route()
                .before(serverRequest -> {
                    log.info("{} {}", serverRequest.method(), serverRequest.path());
                    return serverRequest;
                })
                .POST("/kafka/send", dataHandler::sendMessageToKafka)
                .build();
    }
}
