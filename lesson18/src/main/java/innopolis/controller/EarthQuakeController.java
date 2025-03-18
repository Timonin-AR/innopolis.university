package innopolis.controller;

import innopolis.dto.EarthQuakeCreateRequest;
import innopolis.dto.EarthQuakeResponse;
import innopolis.service.EarthQuakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/earthquake")
public class EarthQuakeController {

    private final EarthQuakeService earthQuakeService;

    @PostMapping
    public ResponseEntity addEarthQuakes(@RequestBody EarthQuakeCreateRequest request) {
        log.info("Импорт данных в БД");
        earthQuakeService.addEarthQuakes(request);
        log.info("Импорт завершен успешно");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{mag}")
    public ResponseEntity<List<EarthQuakeResponse>> getEarthQuakes(@PathVariable Double mag) {
        return ResponseEntity.ok(earthQuakeService.getEarthQuakes(mag));
    }

    @GetMapping()
    public ResponseEntity<List<EarthQuakeResponse>> getEarthQuakesByTime(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(earthQuakeService.getEarthQuakesByTime(start,end));
    }
}
