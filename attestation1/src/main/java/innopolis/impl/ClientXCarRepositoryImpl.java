package innopolis.impl;

import innopolis.entity.ClientXCar;
import innopolis.mappers.ClientXCarMapper;
import innopolis.ClientXCarRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;

public class ClientXCarRepositoryImpl implements ClientXCarRepository {

    private final ClientXCarMapper mapper = new ClientXCarMapper();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private static final String SELECT_ALL = "select * from tire.clients_x_cars;";
    private static final String DELETE_BY_ID = "delete from tire.clients_x_cars cc where cc.id=?;";
    private static final String UPDATE_BY_ID = "update tire.clients_x_cars cc set car_id=?, client_id=? where cc.id=?;";

    @Override
    public List<ClientXCar> findAll() {
        return jdbcTemplate.query(SELECT_ALL, mapper);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void update(Integer id, Integer clientId, Integer carId) {
        jdbcTemplate.update(UPDATE_BY_ID, carId, clientId, id);
    }
}
