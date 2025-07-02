package innopolis.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private String validToken;
    private String expiredToken;
    private final Long testUserId = 123L;
    private final String testUsername = "testuser";

    @BeforeEach
    void setUp() {
        // Генерация тестовых токенов
        validToken = generateTestToken(testUsername, testUserId, false);
        expiredToken = generateTestToken(testUsername, testUserId, true);
    }

    private String generateTestToken(String username, Long userId, boolean expired) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        long expirationTime = expired ?
                System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1) :
                System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTime))
                .signWith(jwtService.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername_ForValidToken() {
        // When
        String username = jwtService.extractUsername(validToken);

        // Then
        assertEquals(testUsername, username);
    }

    @Test
    void extractUserId_ShouldReturnCorrectUserId_ForValidToken() {
        // When
        Long userId = jwtService.extractUserId(validToken);

        // Then
        assertEquals(testUserId, userId);
    }

    @Test
    void isTokenExpired_ShouldReturnFalse_ForValidToken() {
        // When
        boolean isExpired = jwtService.isTokenExpired(validToken);

        // Then
        assertFalse(isExpired);
    }

    @Test
    void isTokenExpired_ShouldReturnTrue_ForExpiredToken() {
        // When
        boolean isExpired = jwtService.isTokenExpired(expiredToken);

        // Then
        assertTrue(isExpired);
    }


    @Test
    void extractAllClaims_ShouldReturnClaims_ForValidToken() {
        // When
        Claims claims = jwtService.extractAllClaims(validToken);

        // Then
        assertNotNull(claims);
        assertEquals(testUsername, claims.getSubject());
        assertEquals(testUserId, claims.get("userId", Long.class));
    }

    @Test
    void getSecretKey_ShouldReturnHS384Key() {
        Key key = jwtService.getSecretKey();
        assertNotNull(key);
        assertEquals("HmacSHA384", key.getAlgorithm());
        assertEquals(48, key.getEncoded().length);
    }

    @Test
    void extractClaim_ShouldApplyFunctionToClaims() {
        // Given
        Function<Claims, String> subjectExtractor = Claims::getSubject;

        // When
        String subject = jwtService.extractClaim(validToken, subjectExtractor);

        // Then
        assertEquals(testUsername, subject);
    }

    @Test
    void extractExpiration_ShouldReturnCorrectDate() {
        // When
        Date expiration = jwtService.extractExpiration(validToken);

        // Then
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }
}
