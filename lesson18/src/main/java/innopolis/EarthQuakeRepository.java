package innopolis;

import innopolis.dto.EarthQuakeResponse;
import innopolis.entity.EarthQuakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EarthQuakeRepository extends JpaRepository<EarthQuakeEntity, Long> {

    List<EarthQuakeEntity> findByMagAfter(Double mag);
    List<EarthQuakeEntity> findByTimeBetween(LocalDateTime start, LocalDateTime end);
}
