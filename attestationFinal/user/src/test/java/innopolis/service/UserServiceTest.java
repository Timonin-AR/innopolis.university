package innopolis.service;

import innopolis.model.dto.JwtRequestDto;
import innopolis.model.dto.UserRegistrationDto;
import innopolis.model.entity.UserEntity;
import innopolis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static innopolis.model.enums.UserRole.ROLE_USER_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final static String TEST_USER = "testuser";
    private final static String PASSWORD = "password";
    private final static String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void registrationUser_ShouldSaveUserWithEncodedPassword() {
        var mail = "test@example.com";
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.registrationUser(new UserRegistrationDto(TEST_USER, PASSWORD, mail));

        assertThat(result.getUsername()).isEqualTo(TEST_USER);
        assertThat(result.getPassword()).isEqualTo(ENCODED_PASSWORD);
        assertThat(result.getEmail()).isEqualTo(mail);
        assertThat(result.getRole()).isEqualTo(ROLE_USER_OWNER);
        assertThat(result.getRegistered()).isNotNull();

        verify(passwordEncoder).encode(PASSWORD);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void authenticate_ShouldReturnJwtToken_WhenCredentialsValid() {
        var testToken = "testToken";
        var user = UserEntity.builder()
                .id(1L)
                .username(TEST_USER)
                .password(ENCODED_PASSWORD)
                .role(ROLE_USER_OWNER)
                .build();

        when(userRepository.findByUsername(TEST_USER)).thenReturn(Optional.of(user));
        when(jwtService.generateJwtToken(user, user.getId())).thenReturn(testToken);

        var response = userService.authenticate(new JwtRequestDto(TEST_USER, PASSWORD));

        assertThat(response.getToken()).isEqualTo(testToken);
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(TEST_USER, PASSWORD));
        verify(jwtService).generateJwtToken(user, user.getId());
    }

    @Test
    void authenticate_ShouldThrowException_WhenUserNotFound() {
        var unknown = "unknown";
        when(userRepository.findByUsername(unknown)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.authenticate(new JwtRequestDto(unknown, PASSWORD)));
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(unknown, PASSWORD));
        verify(userRepository).findByUsername(unknown);
    }

    @Test
    void authenticate_ShouldThrowException_WhenAuthenticationFails() {
        var wrongPassword = "wrongpassword";
        doThrow(new RuntimeException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any());

        assertThrows(RuntimeException.class, () -> userService.authenticate(new JwtRequestDto(TEST_USER, wrongPassword)));
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(TEST_USER, wrongPassword));
        verifyNoInteractions(jwtService);
    }
}
