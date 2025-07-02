package innopolis.aspect;

import innopolis.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
public class JwtAuthAspectTest {
    private final static String BEARER = "Bearer ";
    private final static String VALID_TOKEN = "valid.token";
    private final static String EXPIRED_TOKEN = "expired.token";
    private final static String TOKEN_MISSING = "Token missing";

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtAuthAspect jwtAuthAspect;

    @Test
    void validateToken_ShouldSetUserId_WhenValidToken() {
        var request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, BEARER + VALID_TOKEN);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(jwtService.isTokenExpired(VALID_TOKEN)).thenReturn(false);
        when(jwtService.extractUserId(VALID_TOKEN)).thenReturn(123L);
        jwtAuthAspect.validateToken();

        assertEquals(123L, request.getAttribute("userId"));
        verify(jwtService).isTokenExpired(VALID_TOKEN);
        verify(jwtService).extractUserId(VALID_TOKEN);
    }

    @Test
    void validateToken_ShouldThrowException_WhenNoHeader() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        var exception = assertThrows(RuntimeException.class,
                () -> jwtAuthAspect.validateToken());

        assertEquals(TOKEN_MISSING, exception.getMessage());
    }

    @Test
    void validateToken_ShouldThrowException_WhenInvalidHeaderFormat() {
        var request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, "InvalidFormat");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var exception = assertThrows(RuntimeException.class,
                () -> jwtAuthAspect.validateToken());
        assertEquals(TOKEN_MISSING, exception.getMessage());
    }

    @Test
    void validateToken_ShouldThrowException_WhenTokenExpired() {
        var request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, BEARER + EXPIRED_TOKEN);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(jwtService.isTokenExpired(EXPIRED_TOKEN)).thenReturn(true);

        var exception = assertThrows(RuntimeException.class,
                () -> jwtAuthAspect.validateToken());
        assertEquals("Token expired", exception.getMessage());
        verify(jwtService).isTokenExpired(EXPIRED_TOKEN);
    }

    @Test
    void validateToken_ShouldThrowException_WhenNoRequestContext() {
        RequestContextHolder.resetRequestAttributes();
        assertThrows(IllegalStateException.class,
                () -> jwtAuthAspect.validateToken());
    }
}
