package innopolis.repository.impl;

import innopolis.entity.Car;
import innopolis.entity.Client;
import innopolis.mappers.ClientMapper;
import innopolis.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;
import static java.lang.String.format;

@Slf4j
public class ClientRepositoryImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private final ClientMapper clientMapper = new ClientMapper();

    private static final String FIND_ALL_CLIENTS = "select * from \"tire\".clients";
    private static final String FIND_CLIENT_BY_ID = "select * from \"tire\".clients c where c.id=?";
    private static final String ADD_NEW_CLIENT = "INSERT INTO \"tire\".clients (first_name, midl_name, last_name, date_of_birth, phone_number) VALUES (?,?,?,?,?) RETURNING id";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM \"tire\".clients c where c.id=?";
    private static final String UPDATE_CLIENT_BY_ID = "UPDATE \"tire\".clients c SET first_name=?, midl_name=?, last_name=?, date_of_birth=?, phone_number=?  WHERE c.id=?";
    private static final String DELETE_ALL = """
            with tempCar as (delete from tire.cars tc where tc.client_id in (select id from tire.clients) returning tc.client_id as clientId, id),
            	tempTable as (delete from tire.orders o where o.car_id in (select id from tempCar)),
            	tempCxC as (delete from tire.clients_x_cars cxc where cxc.car_id in (select id from tempCar))
            delete from tire.clients c where c.id in (select clientId from tempCar)
            """;

    @Override
    public List<Client> findAll() {
        return jdbcTemplate.query(FIND_ALL_CLIENTS, clientMapper);
    }

    @Override
    public Integer create(Client client) {
        return jdbcTemplate.query(ADD_NEW_CLIENT,
                (result, row) -> result.getInt(1),
                client.getFirsName(), client.getMidlName(), client.getLastName(), client.getDateOfBirth(), client.getPhoneNumber()).getFirst();
    }

    @Override
    public Client findById(Integer id) {
        return jdbcTemplate.query(FIND_CLIENT_BY_ID, clientMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("%s с заданным идентификатором %d не существует", Client.class.getSimpleName(), id)));
    }

    @Override
    public void deleteById(Integer id) {
        if (jdbcTemplate.update(DELETE_CLIENT_BY_ID, id) == 0)
            throw new NoSuchElementException(format("%s с заданным идентификатором %d не существует", Client.class.getSimpleName(), id));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Integer update(Integer id, Client client) {
        try {
            var updatedRows = jdbcTemplate.update(UPDATE_CLIENT_BY_ID, client.getFirsName(), client.getMidlName(), client.getLastName(), client.getDateOfBirth(), client.getPhoneNumber(), id);
            if (updatedRows == 0) {
                var newClientId = create(client);
                log.warn("{} объект не найден. Создан новый объект по переданному объекту id {}", Client.class.getSimpleName(), newClientId);
                return newClientId;
            }
            return updatedRows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
