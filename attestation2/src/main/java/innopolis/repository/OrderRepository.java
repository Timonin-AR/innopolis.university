package innopolis.repository;

import innopolis.entity.Order;

import java.util.List;

public interface OrderRepository {

    Integer create(Order order);
    Order findById(Integer id);
    List<Order> findAll();
    Integer update(Integer id, Order order);
    void delete(Integer id);
    void deleteAll();



}
