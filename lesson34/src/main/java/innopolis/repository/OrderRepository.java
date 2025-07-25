package innopolis.repository;

import innopolis.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Репозиторий для взаимодействия с базой данных для таблицы с заказами
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
