package innopolis;

import innopolis.entity.Client;

import java.util.List;

public interface ClientRepository {

    Integer create(Client client);
    Client findById(Integer id);
    List<Client> findAll();
    Integer update(Integer id, Client client);
    void deleteById(Integer id);
    void deleteAll();

}
