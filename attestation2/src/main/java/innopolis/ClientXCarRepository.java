package innopolis;

import innopolis.entity.ClientXCar;

import java.util.List;

public interface ClientXCarRepository {

    Integer create(ClientXCar clientXCar);
    ClientXCar findById(Integer id);
    List<ClientXCar> findAll();
    Integer update(Integer id, Integer clientId, Integer carId);
    void delete(Integer id);
    void deleteAll();


}
