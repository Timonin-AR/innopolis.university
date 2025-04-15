package innopolis.impl;

import innopolis.entity.Client;
import innopolis.mappers.ClientMapper;
import innopolis.ClientRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;

public class ClientRepositoryImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private final ClientMapper clientMapper = new ClientMapper();

    private static final String FIND_ALL_CLIENTS = "select * from \"tireService\".clients";
    private static final String ADD_NEW_CLIENT = "INSERT INTO \"tireService\".clients (first_name, midl_name, last_name, date_of_birth, phone_number) VALUES (?,?,?,?,?)";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM \"tireService\".clients c where c.id=?";
    private static final String SET_PHONE_NUMBER_CLIENT_BY_ID = "UPDATE \"tireService\".clients c SET phone_number=? WHERE c.id=?";

    @Override
    public List<Client> findAllClients() {
        return jdbcTemplate.query(FIND_ALL_CLIENTS, clientMapper);
    }

    @Override
    public void createClient(Client client) {
        jdbcTemplate.update(ADD_NEW_CLIENT, client.getFirsName(), client.getMidlName(), client.getLastName(), client.getDateOfBirth(), client.getPhoneNumber());
    }

    @Override
    public void deleteClientById(Integer id) {
        jdbcTemplate.update(DELETE_CLIENT_BY_ID, id);
    }

    @Override
    public void updateClientPhoneNumber(Integer id, Integer newPhoneNumber) {
        jdbcTemplate.update(SET_PHONE_NUMBER_CLIENT_BY_ID, newPhoneNumber, id);
    }
}
