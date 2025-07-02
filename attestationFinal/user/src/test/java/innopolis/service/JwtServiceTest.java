package innopolis.service;

import innopolis.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    private String validToken;
    private String expiredToken;
    private static final String TEST_USERNAME = "testuser";
    private static final Long TEST_USER_ID = 123L;
    private static final String SECRET_KEY = "bbd22a83a5eecbe99df5180136c0c3cc8caeca201be6f21affd3363dcade16f9";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "expirationTime", TimeUnit.HOURS.toMillis(1));

        validToken = generateTestToken(false);
        expiredToken = generateTestToken(true);
    }

    private String generateTestToken(boolean expired) {
        long expiration = expired ?
                System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1) :
                System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1);

        return Jwts.builder()
                .setClaims(Map.of("userId", TEST_USER_ID))
                .setSubject(TEST_USERNAME)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .signWith(getTestSecretKey(), HS256)
                .compact();
    }

    private Key getTestSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void generateJwtToken_ShouldReturnValidToken() {
        var token = jwtService.generateJwtToken(UserEntity.builder()
                .id(1L)
                .username(TEST_USERNAME)
                .build(), TEST_USER_ID);

        assertNotNull(token);
        assertEquals(TEST_USERNAME, jwtService.extractUsername(token));
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        assertEquals(TEST_USERNAME, jwtService.extractUsername(validToken));
    }

    @Test
    void isValidToken_ShouldReturnTrue_ForValidToken() {
        assertTrue(jwtService.isValidToken(validToken, UserEntity.builder().id(1L).username(TEST_USERNAME).build()));
    }

    @Test
    void isValidToken_ShouldReturnFalse_ForExpiredToken() {
        assertFalse(jwtService.isValidToken(expiredToken, userDetails));
    }

    @Test
    void isValidToken_ShouldReturnFalse_ForWrongUser() {
        when(userDetails.getUsername()).thenReturn("wronguser");
        assertFalse(jwtService.isValidToken(validToken, userDetails));
    }

    @Test
    void isTokenExpired_ShouldReturnFalse_ForValidToken() {
        assertFalse(jwtService.isTokenExpired(validToken));
    }

    @Test
    void isTokenExpired_ShouldReturnTrue_ForExpiredToken() {
        assertTrue(jwtService.isTokenExpired(expiredToken));
    }

    @Test
    void extractAllClaims_ShouldReturnClaims() {
        var claims = jwtService.extractAllClaims(validToken);

        assertNotNull(claims);
        assertEquals(TEST_USERNAME, claims.getSubject());
        assertEquals(TEST_USER_ID, claims.get("userId", Long.class));
    }

    @Test
    void getSecretKey_ShouldReturnValidKey() {
        var key = jwtService.getSecretKey();

        assertNotNull(key);
        assertEquals("HmacSHA384", key.getAlgorithm());
    }

    @Test
    void extractClaim_ShouldApplyFunctionToClaims() {
        assertEquals(TEST_USERNAME, jwtService.extractClaim(validToken, Claims::getSubject));
    }
}
