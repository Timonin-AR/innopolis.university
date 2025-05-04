package innopolis.controller;

import innopolis.entity.CarEntity;
import innopolis.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллеры для сервиса машин.
 * Endpoint для взаимодействия через https браузером
 **/
@RestController
@RequestMapping("api/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    /**
     * Метод для добавления информации о машине или обновления данных
     *
     * @param car принимает json с полями как  {@link CarEntity}
     * @return возвращает класс {@link CarEntity}
     */
    @ApiResponse(responseCode = "200", description = "Данные сохранены")
    @Operation(description = "Метод для добавления информации о машине или обновления данных",
            summary = "Добавление/изменение данных о машинах")
    @PostMapping()
    public CarEntity addCar(@Parameter(description = "Json объект CarEntity") @RequestBody CarEntity car) {
        return carService.saveCar(car);
    }

}
