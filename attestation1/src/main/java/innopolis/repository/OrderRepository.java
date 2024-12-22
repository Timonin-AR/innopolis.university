package innopolis.repository;

import innopolis.entity.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAllOrder();
    void createOrder(Order order);
    void deleteOrderById(Integer clientId);
    void updateCost(Integer clientId, Integer costOfWork);

}
