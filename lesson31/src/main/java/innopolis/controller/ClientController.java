package innopolis.controller;

import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import innopolis.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/{id}")
    public ClientDto getClient(@PathVariable Long id) {
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
    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.setSoftDelete(id);
        return String.format("Пользователь под id: %s удален", id);
    }

    /**
     * Метод для добавления или изменения клиента
     *
     * @param client принимает json c полями как у {@link ClientEntity}
     * @return {@link ClientEntity}
     */
    @Operation(description = "Метод для добавления/изменения данных о клиентах",
            summary = "Добавить/Изменить клиента")
    @PostMapping
    public ClientEntity saveClient(@RequestBody ClientEntity client) {
        return clientService.saveClient(client);
    }
}
