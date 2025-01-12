package repository;

import innopolis.entity.Client;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientRepositoryImplTest extends RepositoryBaseTest {
    private static final Client testClient = Client.builder()
            .id(null)
            .firsName("Тест")
            .midlName("Тостер")
            .lastName("Тестеров")
            .dateOfBirth(Timestamp.valueOf("1964-03-03 00:00:00.000"))
            .phoneNumber("+7 (906) 252-75-00")
            .build();

    @Test
    @Order(1)
    void createClient() {
        var clientId = clientRepository.create(testClient);
        testClient.setId(clientId);
        assertThat(clientRepository.findById(clientId))
                .extracting(Client::getId, Client::getFirsName, Client::getMidlName, Client::getLastName, Client::getDateOfBirth, Client::getPhoneNumber)
                .contains(testClient.getId(), testClient.getFirsName(), testClient.getMidlName(), testClient.getLastName(), testClient.getDateOfBirth(), testClient.getPhoneNumber());
    }

    @Test
    @Order(2)
    void findClientById() {
        assertThat(clientRepository.findById(testClient.getId())).isNotNull();
    }

    @Test
    @Order(3)
    void findClientByIdNegativeCase() {
        assertThatThrownBy(() -> clientRepository.findById(1000))
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessage("%s с заданным идентификатором %d не существует", Client.class.getSimpleName(), 1000);
    }

    @Test
    @Order(4)
    void update() {
        var newLastName = "новаяТестовяФамилия";
        testClient.setLastName(newLastName);
        clientRepository.update(testClient.getId(), testClient);
        assertThat(clientRepository.findById(testClient.getId()).getLastName()).isEqualTo(newLastName);
        clientRepository.deleteById(testClient.getId());

    }

    @Test
    @Order(5)
    void updateNegativeCase() {
        var newFirstName = "Новое имя";
        testClient.setFirsName(newFirstName);
        testClient.setId(clientRepository.update(5000, testClient));
        assertThat(clientRepository.findById(testClient.getId()).getFirsName()).isEqualTo(newFirstName);
    }

    @Test
    @Order(6)
    void delete() {
        clientRepository.deleteById(testClient.getId());
    }

    @Test
    @Order(7)
    void deleteNegativeCase() {
        assertThatThrownBy(() -> clientRepository.deleteById(testClient.getId()))
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessage("%s с заданным идентификатором %d не существует", Client.class.getSimpleName(), testClient.getId());
    }

    @Test
    @Order(8)
    void deleteAll() {
        clientRepository.deleteAll();
        assertThat(clientRepository.findAll()).isEmpty();
    }


}
