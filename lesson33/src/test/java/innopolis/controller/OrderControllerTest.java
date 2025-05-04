package innopolis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.dto.OrderDto;
import innopolis.entity.CarEntity;
import innopolis.entity.ClientEntity;
import innopolis.entity.OrderEntity;
import innopolis.security.SecurityConfig;
import innopolis.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {OrderController.class, SecurityConfig.class})
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getOrderTest() throws Exception {
        var orderDto = OrderDto.builder()
                .fio("Тестовый")
                .details("Замены шин")
                .price(5000L)
                .carName("BMW X5")
                .number("FH234RU26")
                .build();
        when(orderService.getOrder(1L)).thenReturn(orderDto);

        mockMvc.perform(get("/api/v1/order/1")).andExpect(status().isOk());
        verify(orderService, times(1)).getOrder(any());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void saveClientTest() throws Exception {
        var carEntity = CarEntity.builder()
                .model("m5")
                .number("AR432RU29")
                .brand("BMW").build();

        var clientEntity = ClientEntity.builder()
                .id(1L)
                .fio("Тестовый")
                .phone("+7 942 432 31 41")
                .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        var orderEntity = OrderEntity.builder()
                .id(1L)
                .car(carEntity)
                .client(clientEntity)
                .price(5000L)
                .details("Замена шин")
                .build();
        when(orderService.saveOrder(orderEntity)).thenReturn(orderEntity);

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntity)))
                .andExpect(status().isOk());

        verify(orderService, times(1)).saveOrder(any());
    }


}
