package innopolis.service;

import innopolis.entity.CarEntity;
import innopolis.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public CarEntity saveCar(CarEntity car){
        return carRepository.save(car);
    }

}
