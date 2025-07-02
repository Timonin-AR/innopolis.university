package innopolis.service;

import innopolis.repository.CarRepository;
import innopolis.entity.CarEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void checkCarService_SaveCar_ReturnCarEntity(){
        var car = CarEntity.builder()
                .id(1L)
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        when(carRepository.save(any(CarEntity.class))).thenReturn(car);

        var savedCar = carService.saveCar(car);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isGreaterThan(0);
    }
}
