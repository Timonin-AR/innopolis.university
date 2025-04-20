package innopolis.controller;

import innopolis.dto.OrderDto;
import innopolis.entity.OrderEntity;
import innopolis.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderEntity addOrder(@RequestBody OrderEntity order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

}
