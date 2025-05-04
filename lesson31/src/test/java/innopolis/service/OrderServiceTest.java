package innopolis.service;

import innopolis.repository.OrderRepository;
import innopolis.dto.OrderDto;
import innopolis.entity.CarEntity;
import innopolis.entity.ClientEntity;
import innopolis.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void checkOrderService_SaveOrder_ReturnSavedOrderEntity() {
        var client = ClientEntity.builder()
                .id(1L)
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var car = CarEntity.builder()
                .id(1L)
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        var order = OrderEntity.builder()
                .id(1L)
                .car(car)
                .client(client)
                .details("Замена шин")
                .price(5000L)
                .build();

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        var savedOrder = orderService.saveOrder(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }

    @Test
    public void checkOrderService_GetOrder_ReturnOrderDto() {
        var client = ClientEntity.builder()
                .id(1L)
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var car = CarEntity.builder()
                .id(1L)
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        var order = OrderEntity.builder()
                .id(1L)
                .car(car)
                .client(client)
                .details("Замена шин")
                .price(5000L)
                .build();

        when(orderRepository.getReferenceById(1L)).thenReturn(order);

        var savedOrder = orderService.getOrder(1L);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder).isInstanceOf(OrderDto.class);
    }

    @Test
    public void checkOrderService_GetOrder_ThrowException() {
        var client = ClientEntity.builder()
                .id(1L)
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(true)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var car = CarEntity.builder()
                .id(1L)
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        var order = OrderEntity.builder()
                .id(1L)
                .car(car)
                .client(client)
                .details("Замена шин")
                .price(5000L)
                .build();

        when(orderRepository.getReferenceById(1L)).thenReturn(order);

        assertThatThrownBy(() -> orderService.getOrder(1L)).isExactlyInstanceOf(RuntimeException.class).hasMessage("Пользователь удален");
    }


}
