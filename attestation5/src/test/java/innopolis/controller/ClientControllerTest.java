package innopolis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.dto.ClientDto;
import innopolis.entity.ClientEntity;
import innopolis.security.SecurityConfig;
import innopolis.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = {ClientController.class, SecurityConfig.class})
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getClientTest() throws Exception {
        var clientDto = ClientDto.builder()
                .fio("Тестовый")
                .phone("+7 942 432 31 41")
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        when(clientService.getClient(1L)).thenReturn(clientDto);

        mockMvc.perform(get("/api/v1/client/1")).andExpect(status().isOk());
        verify(clientService, times(1)).getClient(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteClientTest() throws Exception {
        mockMvc.perform(delete("/api/v1/client/1")).andExpect(status().isOk());
        verify(clientService, times(1)).setSoftDelete(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveClientTest() throws Exception {
        var clientEntity = ClientEntity.builder()
                .id(1L)
                .isDelete(false)
                .fio("Тестовы")
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .phone("+7 942 432 31 41")
                .build();
        when(clientService.saveClient(clientEntity)).thenReturn(clientEntity);

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientEntity)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).saveClient(any());
    }

}
