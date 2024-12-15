package innopolis.repository;

import innopolis.entity.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAllClients();
    void addNewClient(Client car);
    void deleteClientById(Integer id);
    void updateClientPhoneNumber(Integer id, Integer newPhoneNumber);
}
