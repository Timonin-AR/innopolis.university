package innopolis.repository.impl;

import innopolis.entity.Order;
import innopolis.mappers.OrderMapper;
import innopolis.repository.OrderRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;

public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private final OrderMapper orderMapper = new OrderMapper();

    private static final String FIND_ALL_ORDERS = "select * from \"tire\".orders";
    private static final String ADD_NEW_ORDER = "INSERT INTO \"tire\".orders (cost_of_work, car_id, client_id) VALUES (?,?,?)";
    private static final String DELETE_ORDER_BY_CLIENT_ID = "DELETE FROM \"tire\".orders c where c.client_id=?";
    private static final String SET_COST_OF_WORK_BY_CLIENT_ID = "UPDATE \"tire\".orders c SET cost_of_work=? WHERE c.client_id=?";


    @Override
    public List<Order> findAllOrder() {
        return jdbcTemplate.query(FIND_ALL_ORDERS, orderMapper);
    }

    @Override
    public void createOrder(Order order) {
        jdbcTemplate.update(ADD_NEW_ORDER, order.getCostOfWork(), order.getCarId());
    }

    @Override
    public void deleteOrderById(Integer clientId) {
        jdbcTemplate.update(DELETE_ORDER_BY_CLIENT_ID, clientId);
    }

    @Override
    public void updateCost(Integer clientId, Integer costOfWork) {
        jdbcTemplate.update(SET_COST_OF_WORK_BY_CLIENT_ID, costOfWork, clientId);
    }
}
