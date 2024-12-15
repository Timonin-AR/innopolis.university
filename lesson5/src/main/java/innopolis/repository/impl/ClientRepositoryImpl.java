package innopolis.repository.impl;

import innopolis.db.DatabaseService;
import innopolis.entity.Client;
import innopolis.mappers.ClientMapper;
import innopolis.repository.ClientRepository;

import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    
    private final DatabaseService db = new DatabaseService();
    private final ClientMapper clientMapper = new ClientMapper();

    private static final String FIND_ALL_CLIENTS = "select * from \"tireService\".clients";
    private static final String ADD_NEW_CLIENT = "INSERT INTO \"tireService\".clients (first_name, midl_name, last_name, date_of_birth, phone_number) VALUES (?,?,?,?,?)";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM \"tireService\".clients c where c.id=?";
    private static final String SET_PHONE_NUMBER_CLIENT_BY_ID = "UPDATE \"tireService\".clients c SET phone_number=? WHERE c.id=?";

    @Override
    public List<Client> findAllClients() {
        return db.template.query(FIND_ALL_CLIENTS, clientMapper);
    }

    @Override
    public void addNewClient(Client client) {
        db.template.update(ADD_NEW_CLIENT, client.getFirsName(), client.getMidlName(), client.getLastName(), client.getDateOfBirth(), client.getPhoneNumber());
    }

    @Override
    public void deleteClientById(Integer id) {
        db.template.update(DELETE_CLIENT_BY_ID, id);
    }

    @Override
    public void updateClientPhoneNumber(Integer id, Integer newPhoneNumber) {
        db.template.update(SET_PHONE_NUMBER_CLIENT_BY_ID, newPhoneNumber, id);
    }
}
