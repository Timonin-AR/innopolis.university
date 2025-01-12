package repository;

import innopolis.entity.Car;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static innopolis.repository.impl.CarRepositoryImpl.clearCarWithTestName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarRepositoryImplTest extends RepositoryBaseTest {
    private Car testCar;
    private Car car = Car.builder()
            .model("Testla")
            .clientId(1)
            .number("o777oo26")
            .name("test")
            .build();

    @AfterAll
    static void clearTestData() {
        clearCarWithTestName();
    }

    @Test
    void selectAll() {
        assertThat(carRepository.findAll().size()).isGreaterThan(0);
    }

    @Test
    void selectById() {
        assertThat(carRepository.findById(1).getId()).isEqualTo(1);
    }

    @Test
    void selectByIdNegativeCase() {
        assertThatThrownBy(() -> carRepository.findById(1000))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Car с заданным идентификатором 1000 не существует");
    }

    @Test
    void selectByNumber() {
        assertThat(carRepository.findByNumber("B222BB").getNumber()).isEqualTo("B222BB");
    }

    @Test
    void selectByNumberNegativeCase() {
        assertThatThrownBy(() -> carRepository.findByNumber("ТЕСТ"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Car с заданным идентификатором ТЕСТ не существует");
    }

    @Test
    void createCar() {
        carRepository.create(car);
        testCar = carRepository.findByNumber(car.getNumber());
        assertThat(testCar.getId())
                .isNotNull()
                .isNotNegative()
                .isNotZero();
        assertThat(testCar)
                .isNotNull()
                .extracting(Car::getModel, Car::getClientId, Car::getNumber, Car::getName)
                .containsExactly(car.getModel(), car.getClientId(), car.getNumber(), car.getName());
    }

    @Test
    void deleteCar() {
        testCar = carRepository.findById(carRepository.create(car));
        carRepository.deleteById(testCar.getId());
        assertThatThrownBy(() -> carRepository.deleteById(testCar.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Car с заданным идентификатором %s не существует", testCar.getId());
    }

    @Test
    void updateCar() {
        var newName = "newName";
        testCar = carRepository.findById(2);
        testCar.setName(newName);
        carRepository.update(2, testCar);
        assertThat(carRepository.findById(2).getName()).isEqualTo(newName);
    }

    @Test
    void updateCarIsNotExist() {
        var carId = carRepository.update(100, car);
        assertThat(carRepository.findById(carId)).isNotNull();
    }

    @Test
    void updateCarException() {
        car.setClientId(null);
        assertThatThrownBy(()-> carRepository.update(2, car)).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteAll() {
        carRepository.deleteAll();
        assertThat(carRepository.findAll()).isEmpty();
    }
}
