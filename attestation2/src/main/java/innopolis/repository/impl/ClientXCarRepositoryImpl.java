package innopolis.repository.impl;

import innopolis.entity.Client;
import innopolis.entity.ClientXCar;
import innopolis.mappers.ClientXCarMapper;
import innopolis.repository.ClientXCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;
import static java.lang.String.format;

@Slf4j
public class ClientXCarRepositoryImpl implements ClientXCarRepository {

    private final ClientXCarMapper mapper = new ClientXCarMapper();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private static final String SELECT_ALL = "select * from tire.clients_x_cars;";
    private static final String SELECT_BY_ID = "select * from tire.clients_x_cars cxc where cxc.id=?;";
    private static final String DELETE_BY_ID = "delete from tire.clients_x_cars cc where cc.id=?;";
    private static final String UPDATE_BY_ID = "update tire.clients_x_cars cc set car_id=?, client_id=? where cc.id=?;";
    private static final String DELETE_ALL = "TRUNCATE TABLE \"tire\".clients_x_cars";
    private static final String INSERT_NEW_RELATION = "INSERT INTO \"tire\".clients_x_cars (car_id, client_id) VALUES (?,?) returning id";

    @Override
    public Integer create(ClientXCar clientXCar) {
        return jdbcTemplate.query(INSERT_NEW_RELATION,
                (result, row) -> result.getInt(1),
                clientXCar.getCarId(), clientXCar.getClientId()).getFirst();
    }

    @Override
    public ClientXCar findById(Integer id) {
        return jdbcTemplate.query(SELECT_BY_ID, mapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("%s с заданным идентификатором %d не существует", ClientXCar.class.getSimpleName(), id)));
    }

    @Override
    public List<ClientXCar> findAll() {
        return jdbcTemplate.query(SELECT_ALL, mapper);
    }

    @Override
    public void delete(Integer id) {
        if (jdbcTemplate.update(DELETE_BY_ID, id) == 0)
            throw new NoSuchElementException(format("%s с заданным идентификатором %d не существует", ClientXCar.class.getSimpleName(), id));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Integer update(Integer id, Integer clientId, Integer carId) {
        try {
            var updatedRows = jdbcTemplate.update(UPDATE_BY_ID, carId, clientId, id);
            if (updatedRows == 0) {
                var newClientId = create(ClientXCar.builder().id(null).carId(carId).clientId(clientId).build());
                log.warn("{} объект не найден. Создан новый объект по переданному объекту id {}", ClientXCar.class.getSimpleName(), newClientId);
                return newClientId;
            }
            return updatedRows;
        } catch (Exception e) {
            throw new RuntimeException(format("%s с заданным идентификатором %d не существует", ClientXCar.class.getSimpleName(), id));
        }
    }
}
