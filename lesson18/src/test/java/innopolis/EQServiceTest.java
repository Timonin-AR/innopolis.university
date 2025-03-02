package innopolis;

import innopolis.entity.EarthQuakeEntity;
import innopolis.repository.EarthQuakeRepository;
import innopolis.service.EarthQuakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EQServiceTest {

    @Mock
    private EarthQuakeRepository repository;

    @InjectMocks
    private EarthQuakeService service;


    @Test
    void getEarthQuakesTest() {
        when(repository.findByMagAfter(5D)).thenReturn(List.of(new EarthQuakeEntity()));
        assertThat(service.getEarthQuakes(5D)).isNotNull();
    }

    @Test
    void getEarthQuakesByTimeTest() {
        when(repository.findByTimeBetween(LocalDateTime.parse("2025-01-27T01:46:24.368"), LocalDateTime.parse("2025-02-27T01:46:24.368"))).thenReturn(List.of(new EarthQuakeEntity()));
        assertThat(service.getEarthQuakesByTime(LocalDateTime.parse("2025-01-27T01:46:24.368"), LocalDateTime.parse("2025-02-27T01:46:24.368"))).isNotNull();
    }
}
