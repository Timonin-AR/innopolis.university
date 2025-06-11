package innopolis.controller;

import innopolis.dto.OrderDto;
import innopolis.entity.OrderEntity;
import innopolis.handler.KafkaHandler;
import innopolis.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Контроллеры для сервиса заказов.
 * Endpoint для взаимодействия через https браузером
 **/
@Slf4j
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final KafkaHandler dataHandler;
    private final OrderService orderService;

    /**
     * Метод для добавления или обновления заказа
     *
     * @param order принимает json c полями как у {@link OrderEntity}
     * @return {@link OrderEntity}
     */
    @Operation(description = "Метод для добавления/изменения данных о заказе",
            summary = "Добавить/Изменить заказ")
    @PostMapping
    public OrderEntity addOrder(@RequestBody OrderEntity order) {
        return orderService.saveOrder(order);
    }

    /**
     * Метод для получения информации о заказе
     *
     * @param id заказа в базе данных
     * @return {@link OrderDto}
     */
    @Operation(description = "Метод для получения информации о заказе",
            summary = "Получить информацию о заказе")
    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }



    @PostMapping("/kafka/send")
    public ResponseEntity<Mono<?>> sendOrder(@RequestBody OrderEntity order) {
        log.info("POST /kafka/sendOrder");
        return ResponseEntity.ok(dataHandler.sendMessageToKafka(order));
    }

}
