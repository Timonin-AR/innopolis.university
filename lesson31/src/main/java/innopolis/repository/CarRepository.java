package innopolis.repository;

import innopolis.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для взаимодействия с базой данных для таблицы с машинами
 */
@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
}
