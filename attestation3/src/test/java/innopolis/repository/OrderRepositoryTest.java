package innopolis.repository;

import innopolis.entity.CarEntity;
import innopolis.entity.ClientEntity;
import innopolis.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    public OrderRepository orderRepository;

    @Test
    public void checkOrderRepository_Save_ReturnSavedOrder() {
        var client = ClientEntity.builder()
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var car = CarEntity.builder()
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        var order = OrderEntity.builder()
                .car(car)
                .client(client)
                .details("Замена шин")
                .price(5000L)
                .build();

        var savedOrder = orderRepository.save(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }
}
