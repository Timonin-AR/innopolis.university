package innopolis.service;

import innopolis.dto.OrderDto;
import innopolis.entity.OrderEntity;
import innopolis.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public OrderDto getOrder(Long id) {
        var orderEntity = orderRepository.getReferenceById(id);
        if (orderEntity.getClient().getIsDelete())
            throw new RuntimeException("Пользователь удален");

        return OrderDto.builder()
                .fio(orderEntity.getClient().getFio())
                .carName(orderEntity.getCar().getBrand() + orderEntity.getCar().getModel())
                .number(orderEntity.getCar().getNumber())
                .details(orderEntity.getDetails())
                .price(orderEntity.getPrice())
                .build();
    }

}
