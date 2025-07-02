package innopolis.controller;

import innopolis.model.dto.JwtRequestDto;
import innopolis.model.dto.JwtResponseDto;
import innopolis.model.dto.UserRegistrationDto;
import innopolis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Метод для регистрации новых пользователей")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserRegistrationDto userRegistrationDto) {
        var user = userService.registrationUser(userRegistrationDto);
        return ResponseEntity.ok(String.format("Пользователь %s ваша регистрация прошла успешно. Дата регистрации %s", user.getUsername(), user.getRegistered()));
    }

    @Operation(summary = "Метод для авторизации и аутентификации")
    @PostMapping("/auth")
    public ResponseEntity<JwtResponseDto> createAuthToken(@RequestBody JwtRequestDto request) throws BadCredentialsException {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
