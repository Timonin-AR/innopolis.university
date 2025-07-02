package innopolis.service;

import innopolis.model.dto.JwtRequestDto;
import innopolis.model.dto.JwtResponseDto;
import innopolis.model.dto.UserRegistrationDto;
import innopolis.model.entity.UserEntity;
import innopolis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static innopolis.model.enums.UserRole.ROLE_USER_OWNER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserEntity registrationUser(UserRegistrationDto user) {
        return userRepository.save(UserEntity.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .role(ROLE_USER_OWNER)
                .registered(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    public JwtResponseDto authenticate(JwtRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new JwtResponseDto(jwtService.generateJwtToken(user, user.getId()));
    }


}
