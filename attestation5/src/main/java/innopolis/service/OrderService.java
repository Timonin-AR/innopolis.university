package innopolis.service;

import innopolis.dto.OrderDto;
import innopolis.entity.OrderEntity;
import innopolis.kafka.KafkaConsumer;
import innopolis.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Класс описывающий бизнес логику взаимодействия с информацией о заказе
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Cacheable(cacheNames = "order", key = "#id")
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
