package innopolis.controller;

import innopolis.dto.OrderDto;
import innopolis.entity.OrderEntity;
import innopolis.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллеры для сервиса заказов.
 * Endpoint для взаимодействия через https браузером
 **/
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * Метод для добавления или обновления заказа
     *
     * @param order принимает json c полями как у {@link OrderEntity}
     * @return {@link OrderEntity}
     */
    @ApiResponse(responseCode = "200",  description = "Данные сохранены")
    @Operation(description = "Метод для добавления/изменения данных о заказе",
            summary = "Добавить/Изменить заказ")
    @PostMapping
    public OrderEntity addOrder(@Parameter(description = "Json объект OrderEntity") @RequestBody OrderEntity order) {
        return orderService.saveOrder(order);
    }

    /**
     * Метод для получения информации о заказе
     *
     * @param id заказа в базе данных
     * @return {@link OrderDto}
     */
    @ApiResponse(responseCode = "200",  description = "Данные найдены и переданы")
    @Operation(description = "Метод для получения информации о заказе",
            summary = "Получить информацию о заказе")
    @GetMapping("/{id}")
    public OrderDto getOrder(@Parameter(description = "id Заказа") @PathVariable Long id) {
        return orderService.getOrder(id);
    }

}
