package innopolis.service;

import innopolis.repository.ClientRepository;
import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void checkClientService_SaveClient_ReturnClientEntity() {
        var client = ClientEntity.builder()
                .id(1L)
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(client);

        var savedCar = clientService.saveClient(client);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isGreaterThan(0);
    }

    @Test
    public void checkClientService_GetClient_ReturnClientDto() {
        var client = ClientEntity.builder()
                .id(7L)
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        when(clientRepository.getReferenceById(7L)).thenReturn(client);

        var gotClient = clientService.getClient(7L);

        assertThat(gotClient).isNotNull();
        assertThat(gotClient).isInstanceOf(ClientDto.class);
    }

    @Test
    public void checkClientService_SetSoftDelete_ClientIsDelete() {
        var client = ClientEntity.builder()
                .id(7L)
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        when(clientRepository.getReferenceById(7L)).thenReturn(client);
        clientService.setSoftDelete(7L);

        assertThat(clientRepository.getReferenceById(7L).getIsDelete()).isTrue();
    }


}
