package innopolis;

import innopolis.entity.ClientXCar;

import java.util.List;

public interface ClientXCarRepository {

    List<ClientXCar> findAll();

    void delete(Integer id);

    void update(Integer id, Integer clientId, Integer carId);
}
