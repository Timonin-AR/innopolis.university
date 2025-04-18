package innopolis.controller;

import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import innopolis.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{id}")
    public ClientDto getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.setSoftDelete(id);
        return String.format("Пользователь под id: %s удален", id);
    }

    @PostMapping
    public ClientEntity saveClient(@RequestBody ClientEntity client) {
        return clientService.saveClient(client);
    }
}
