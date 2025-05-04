package innopolis.service;

import innopolis.entity.CarEntity;
import innopolis.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Класс описывающий бизнес логику взаимодействия с информацией о машине
 */
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public CarEntity saveCar(CarEntity car){
        return carRepository.save(car);
    }

}
