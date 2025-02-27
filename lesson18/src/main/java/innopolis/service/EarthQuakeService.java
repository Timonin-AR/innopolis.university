package innopolis.service;

import innopolis.dto.EarthQuakeCreateRequest;
import innopolis.dto.EarthQuakeResponse;
import innopolis.entity.EarthQuakeEntity;
import innopolis.repository.EarthQuakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthQuakeService {

    private final EarthQuakeRepository repository;

    public void addEarthQuakes(EarthQuakeCreateRequest request) {
        var earthQuakes = request.getFeatures().stream().map(earthQuake -> EarthQuakeEntity
                .builder()
                .title(earthQuake.getProperties().getTitle())
                .mag(earthQuake.getProperties().getMag())
                .place(earthQuake.getProperties().getPlace())
                .time(LocalDateTime.ofInstant(Instant.ofEpochMilli(earthQuake.getProperties().getTime()), ZoneId.systemDefault()))
                .build()).toList();
        repository.saveAll(earthQuakes);
    }

    public List<EarthQuakeResponse> getEarthQuakes(Double mag) {
        return repository.findByMagAfter(mag).stream()
                .map(entity -> EarthQuakeResponse
                        .builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .mag(entity.getMag())
                        .place(entity.getPlace())
                        .time(entity.getTime())
                        .build())
                .toList();
    }

    public List<EarthQuakeResponse> getEarthQuakesByTime(LocalDateTime start, LocalDateTime end) {
        return repository.findByTimeBetween(start, end).stream()
                .map(entity -> EarthQuakeResponse
                        .builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .mag(entity.getMag())
                        .place(entity.getPlace())
                        .time(entity.getTime())
                        .build())
                .toList();
    }
}
