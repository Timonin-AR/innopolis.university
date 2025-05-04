package innopolis.service;

import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import innopolis.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Класс описывающий бизнес логику взаимодействия с информацией о клиенте
 */
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientEntity saveClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Cacheable(cacheNames = "client", key = "#id")
    public ClientDto getClient(Long id) {
        var clientEntity = clientRepository.getReferenceById(id);
        return ClientDto.builder()
                .fio(clientEntity.getFio())
                .phone(clientEntity.getPhone())
                .dateOfBirth(clientEntity.getDateOfBirth())
                .build();
    }

    @CacheEvict(cacheNames = "client", key = "#id")
    public void setSoftDelete(Long id) {
        var clientEntity = clientRepository.getReferenceById(id);
        clientEntity.setIsDelete(true);
        saveClient(clientEntity);
    }
}
