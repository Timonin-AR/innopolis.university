package innopolis.repository.impl;

import innopolis.entity.Order;
import innopolis.mappers.OrderMapper;
import innopolis.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;
import static java.lang.String.format;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private final OrderMapper orderMapper = new OrderMapper();

    private static final String FIND_ALL_ORDERS = "select * from \"tire\".orders";
    private static final String FIND_BY_ID = "select * from \"tire\".orders o where o.id =?";
    private static final String INSERT_ORDER = "INSERT INTO \"tire\".orders (cost_of_work, car_id) VALUES (?,?) RETURNING id";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM \"tire\".orders c where c.id=?";
    private static final String UPDATE_ORDER_BY_ID = "UPDATE \"tire\".orders c SET cost_of_work=?, car_id=? WHERE c.id=?";
    private static final String DELETE_ALL = "TRUNCATE TABLE \"tire\".orders";


    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(FIND_ALL_ORDERS, orderMapper);
    }

    @Override
    public Integer create(Order order) {
        return jdbcTemplate.query(INSERT_ORDER,
                (result, row) -> result.getInt(1),
                order.getCostOfWork(), order.getCarId()).getFirst();
    }

    @Override
    public Order findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, orderMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("%s с заданным идентификатором %d не существует", Order.class.getSimpleName(), id)));
    }

    @Override
    public void delete(Integer id) {
        if (jdbcTemplate.update(DELETE_ORDER_BY_ID, id) == 0)
            throw new NoSuchElementException(format("%s с заданным идентификатором %d не существует", Order.class.getSimpleName(), id));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Integer update(Integer id, Order order) {
        try {
            var updatedRows = jdbcTemplate.update(UPDATE_ORDER_BY_ID, order.getCostOfWork(), order.getCarId(), id);
            if (updatedRows == 0) {
                var newIdOrder = create(order);
                log.warn("{} с заданным идентификатором {} не существует. Создан новый объект под id {}", Order.class.getSimpleName(), id, newIdOrder);
                return newIdOrder;
            }
            return updatedRows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
