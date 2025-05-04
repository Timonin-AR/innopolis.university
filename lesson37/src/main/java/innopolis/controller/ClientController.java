package innopolis.controller;

import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import innopolis.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллеры для сервиса клиент.
 * Endpoint для взаимодействия через https браузером
 **/
@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    /**
     * Метод для получения информации о клиенте
     *
     * @param id клиента в базе данных
     * @return {@link ClientDto}
     */
    @Operation(description = "Метод для получения информации о клиенте",
            summary = "Получение данных о клиенте")
    @ApiResponse(responseCode = "200",  description = "Данные найдены и переданы")
    @GetMapping("/{id}")
    public ClientDto getClient(@Parameter(description = "id Клиента") @PathVariable Long id) {
        return clientService.getClient(id);
    }

    /**
     * Метод для проставления статуса клиенту "Удален"
     *
     * @param id клиента в базе данных
     * @return уведомление, что клиент удален
     */
    @Operation(description = "Метод для проставления статуса клиенту \"Удален\"",
            summary = "Удаление клиента")
    @ApiResponse(responseCode = "200",  description = "Пользователь удален")
    @DeleteMapping("/{id}")
    public String deleteClient(@Parameter(description = "id Клиента")@PathVariable Long id) {
        clientService.setSoftDelete(id);
        return String.format("Пользователь под id: %s удален", id);
    }

    /**
     * Метод для добавления или изменения клиента
     *
     * @param client принимает json c полями как у {@link ClientEntity}
     * @return {@link ClientEntity}
     */
    @ApiResponse(responseCode = "200",  description = "Данные сохранены")
    @Operation(description = "Метод для добавления/изменения данных о клиентах",
            summary = "Добавить/Изменить клиента")
    @PostMapping
    public ClientEntity saveClient(@Parameter(description = "Json объект ClientEntity")@RequestBody ClientEntity client) {
        return clientService.saveClient(client);
    }
}
