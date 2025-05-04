package innopolis.repository;

import innopolis.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Репозиторий для взаимодействия с базой данных для таблицы с клиентами
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
