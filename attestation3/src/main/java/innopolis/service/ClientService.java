package innopolis.service;

import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import innopolis.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientEntity saveClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    public ClientDto getClient(Long id) {
        var clientEntity = clientRepository.getReferenceById(id);
        return ClientDto.builder()
                .fio(clientEntity.getFio())
                .phone(clientEntity.getPhone())
                .dateOfBirth(clientEntity.getDateOfBirth())
                .build();
    }

    public void setSoftDelete(Long id) {
        var clientEntity = clientRepository.getReferenceById(id);
        clientEntity.setIsDelete(true);
        saveClient(clientEntity);
    }
}
