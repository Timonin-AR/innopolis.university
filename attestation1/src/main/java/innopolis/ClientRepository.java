package innopolis;

import innopolis.entity.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAllClients();
    void createClient(Client car);
    void deleteClientById(Integer id);
    void updateClientPhoneNumber(Integer id, Integer newPhoneNumber);
}
