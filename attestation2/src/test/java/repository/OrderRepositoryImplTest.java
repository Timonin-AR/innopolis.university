package repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryImplTest extends RepositoryBaseTest {
    private static final innopolis.entity.Order testOrder = innopolis.entity.Order.builder()
            .id(null)
            .costOfWork(4444)
            .carId(7)
            .build();

    @Test
    @Order(0)
    void findAll() {
        assertThat(orderRepository.findAll().size()).isGreaterThan(0);
    }

    @Test
    @Order(1)
    void createClient() {
        var orderId = orderRepository.create(testOrder);
        testOrder.setId(orderId);
        assertThat(orderRepository.findById(orderId))
                .extracting(innopolis.entity.Order::getId, innopolis.entity.Order::getCarId, innopolis.entity.Order::getCostOfWork)
                .contains(testOrder.getId(), testOrder.getCarId(), testOrder.getCostOfWork());
    }

    @Test
    @Order(2)
    void findClientById() {
        assertThat(orderRepository.findById(testOrder.getId())).isNotNull();
    }

    @Test
    @Order(3)
    void findClientByIdNegativeCase() {
        assertThatThrownBy(() -> orderRepository.findById(1000))
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessage("%s с заданным идентификатором %d не существует", innopolis.entity.Order.class.getSimpleName(), 1000);
    }

    @Test
    @Order(4)
    void update() {
        var newCarId = 5;
        testOrder.setCarId(5);
        orderRepository.update(testOrder.getId(), testOrder);
        assertThat(orderRepository.findById(testOrder.getId()).getCarId()).isEqualTo(newCarId);
    }

    @Test
    @Order(5)
    void updateNegativeCaseException() {
        testOrder.setCarId(null);
        assertThatThrownBy(() -> orderRepository.update(testOrder.getId(), testOrder))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    @Order(6)
    void updateNegativeCase() {
        var costOfWork = 33333;
        testOrder.setCostOfWork(costOfWork);
        testOrder.setCarId(5);
        testOrder.setId(orderRepository.update(5000, testOrder));
        assertThat(orderRepository.findById(testOrder.getId()).getCostOfWork()).isEqualTo(costOfWork);
    }

    @Test
    @Order(7)
    void delete() {
        orderRepository.delete(testOrder.getId());
    }

    @Test
    @Order(8)
    void deleteNegativeCase() {
        assertThatThrownBy(() -> orderRepository.delete(testOrder.getId()))
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessage("%s с заданным идентификатором %d не существует", innopolis.entity.Order.class.getSimpleName(), testOrder.getId());
    }

    @Test
    @Order(9)
    void deleteAll() {
        orderRepository.deleteAll();
        assertThat(orderRepository.findAll()).isEmpty();
    }

}
