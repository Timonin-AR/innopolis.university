package innopolis.controller;

import innopolis.entity.CarEntity;
import innopolis.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping()
    public CarEntity addCar(@RequestBody CarEntity car) {
        return carService.saveCar(car);
    }

}
