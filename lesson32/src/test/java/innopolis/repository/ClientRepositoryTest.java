package innopolis.repository;

import innopolis.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    public ClientRepository clientRepository;


    @Test
    public void checkClientRepository_Save_ReturnSavedClient() {
        var client = ClientEntity.builder()
                .fio("Петров Иван Иваныч")
                .phone("891883872133")
                .isDelete(false)
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();


        var savedClient = clientRepository.save(client);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isGreaterThan(0);
    }
}
