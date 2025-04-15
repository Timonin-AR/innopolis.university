package innopolis.repository;

import innopolis.entity.CarEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Unit test for simple App.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarRepositoryTest {

    @Autowired
    public CarRepository carRepository;


    @Test
    public void checkCarRepository_Save_ReturnSavedCar() {
        var car = CarEntity.builder()
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        var savedCar = carRepository.save(car);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isGreaterThan(0);
    }
}
