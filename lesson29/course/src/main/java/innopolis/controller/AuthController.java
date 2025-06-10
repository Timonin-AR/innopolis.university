package innopolis.controller;

import innopolis.dto.AuthRequest;
import innopolis.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static innopolis.security.SecurityConfig.passwordEncoder;
import static innopolis.security.SecurityConfig.userDetailsService;
import static innopolis.utils.JwtUtils.generateToken;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            log.info("Логинюсь");

            log.info("Attempting authentication for user: {}", authRequest.getUsername());

            // Проверка перед аутентификацией
            UserDetails user = userDetailsService().loadUserByUsername(authRequest.getUsername());
            log.info("User found: {}", user.getUsername());
            log.info("Password matches: {}",
                    passwordEncoder().matches(authRequest.getPassword(), user.getPassword()));

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String token = generateToken(authRequest.getUsername());
            log.info("Генерю токен {}", token);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello, you are authenticated!");
    }
}
