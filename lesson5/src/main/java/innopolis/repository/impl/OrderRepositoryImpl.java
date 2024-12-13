package innopolis.repository.impl;

import innopolis.db.DatabaseService;
import innopolis.entity.Client;
import innopolis.entity.Order;
import innopolis.mappers.OrderMapper;
import innopolis.repository.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    private final DatabaseService db = new DatabaseService();
    private final OrderMapper orderMapper = new OrderMapper();

    private static final String FIND_ALL_ORDERS = "select * from \"tireService\".orders";
    private static final String ADD_NEW_ORDER = "INSERT INTO \"tireService\".orders (cost_of_work, car_id, client_id) VALUES (?,?,?)";
    private static final String DELETE_ORDER_BY_CLIENT_ID = "DELETE FROM \"tireService\".orders c where c.client_id=?";
    private static final String SET_COST_OF_WORK_BY_CLIENT_ID = "UPDATE \"tireService\".orders c SET cost_of_work=? WHERE c.client_id=?";


    @Override
    public List<Order> findAllOrder() {
        return db.template.query(FIND_ALL_ORDERS, orderMapper);
    }

    @Override
    public void addNewOrder(Order order) {
        db.template.update(ADD_NEW_ORDER, order.getCostOfWork(), order.getCarId(), order.getClientId());
    }

    @Override
    public void deleteOrderById(Integer clientId) {
        db.template.update(DELETE_ORDER_BY_CLIENT_ID, clientId);
    }

    @Override
    public void updateCost(Integer clientId, Integer costOfWork) {
        db.template.update(SET_COST_OF_WORK_BY_CLIENT_ID, costOfWork, clientId);
    }
}
