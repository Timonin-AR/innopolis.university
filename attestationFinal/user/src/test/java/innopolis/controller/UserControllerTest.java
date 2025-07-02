package innopolis.controller;

import innopolis.model.dto.JwtRequestDto;
import innopolis.model.dto.JwtResponseDto;
import innopolis.model.dto.UserRegistrationDto;
import innopolis.model.entity.UserEntity;
import innopolis.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void registration_ShouldReturnSuccessMessage() throws Exception {
        var user = new UserEntity();
        user.setUsername("user");
        user.setRegistered(Timestamp.valueOf(LocalDateTime.now()));

        when(userService.registrationUser(any(UserRegistrationDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"pass\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void createAuthToken_ShouldReturnJwtResponse() throws Exception {
        when(userService.authenticate(any(JwtRequestDto.class))).thenReturn(new JwtResponseDto("token"));

        mockMvc.perform(post("/api/v1/user/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void createAuthToken_ShouldThrowBadCredentials() throws Exception {
        when(userService.authenticate(any(JwtRequestDto.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/user/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }
}
