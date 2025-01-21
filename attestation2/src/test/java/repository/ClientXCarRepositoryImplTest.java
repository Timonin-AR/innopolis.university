package repository;

import innopolis.entity.ClientXCar;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientXCarRepositoryImplTest extends RepositoryBaseTest {
    private static final ClientXCar testRelation = ClientXCar.builder()
            .id(null)
            .clientId(3)
            .carId(7)
            .build();

    @Test
    @Order(0)
    void findAll() {
        assertThat(clientXCarRepository.findAll().size()).isGreaterThan(0);
    }

    @Test
    @Order(1)
    void createClient() {
        var relationId = clientXCarRepository.create(testRelation);
        testRelation.setId(relationId);
        assertThat(clientXCarRepository.findById(relationId))
                .extracting(ClientXCar::getId, ClientXCar::getClientId, ClientXCar::getCarId)
                .contains(testRelation.getId(), testRelation.getClientId(), testRelation.getCarId());
    }

    @Test
    @Order(2)
    void findClientById() {
        assertThat(clientXCarRepository.findById(testRelation.getId())).isNotNull();
    }

    @Test
    @Order(3)
    void findClientByIdNegativeCase() {
        assertThatThrownBy(() -> clientXCarRepository.findById(1000))
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessage("%s с заданным идентификатором %d не существует", ClientXCar.class.getSimpleName(), 1000);
    }

    @Test
    @Order(4)
    void update() {
        var newCarId = 5;
        testRelation.setCarId(5);
        clientXCarRepository.update(testRelation.getId(), testRelation.getClientId(), testRelation.getCarId());
        assertThat(clientXCarRepository.findById(testRelation.getId()).getCarId()).isEqualTo(newCarId);
    }

    @Test
    @Order(5)
    void updateNegativeCase() {
        var newClientId = 4;
        testRelation.setClientId(newClientId);
        testRelation.setId(clientXCarRepository.update(5000, testRelation.getClientId(), testRelation.getCarId()));
        assertThat(clientXCarRepository.findById(testRelation.getId()).getClientId()).isEqualTo(newClientId);
    }

    @Test
    @Order(6)
    void delete() {
        clientXCarRepository.delete(testRelation.getId());
    }

    @Test
    @Order(7)
    void deleteNegativeCase() {
        assertThatThrownBy(() -> clientXCarRepository.delete(testRelation.getId()))
                .isExactlyInstanceOf(NoSuchElementException.class)
                .hasMessage("%s с заданным идентификатором %d не существует", ClientXCar.class.getSimpleName(), testRelation.getId());
    }

    @Test
    @Order(8)
    void deleteAll() {
        clientXCarRepository.deleteAll();
        assertThat(clientXCarRepository.findAll()).isEmpty();
    }


}
