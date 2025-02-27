package innopolis;

import innopolis.controller.EarthQuakeController;
import innopolis.service.EarthQuakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EQControllerTest {

    @Mock
    private EarthQuakeService earthQuakeService;


    @InjectMocks
    private EarthQuakeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void addEarthQuakesTest() throws Exception {
        mockMvc.perform(post("/api/v1/earthquake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new String(Files.readAllBytes(Path.of("src/main/resources/dataOfEarthQuake.json")))))
                .andExpect(status().isOk());
        verify(earthQuakeService, times(1)).addEarthQuakes(any());
    }

    @Test
    void getEarthQuakesTest() throws Exception {
        mockMvc.perform(get("/api/v1/earthquake/{mag}", 5))
                .andExpect(status().isOk());
        verify(earthQuakeService, times(1)).getEarthQuakes(5D);
    }

    @Test
    void getEarthQuakesByTimeTest() throws Exception {
        mockMvc.perform(get("/api/v1/earthquake")
                        .queryParam("start", "2025-01-27T01:46:24.368")
                        .queryParam("end", "2025-02-27T01:46:24.368"))
                .andExpect(status().isOk());
        verify(earthQuakeService, times(1)).getEarthQuakesByTime(any(), any());
    }


}
