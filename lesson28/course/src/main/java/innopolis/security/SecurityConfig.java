package innopolis.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthEntryPoint customAuthEntryPoint;

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        var viewer = User.withUsername("viewer")
                .password("{noop}qwerty")
                .roles("VIEWER")
                .build();

        var user = User.withUsername("user")
                .password("{noop}qwerty")
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin, viewer);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers(HttpMethod.GET, "/course/**").hasAnyRole("VIEWER", "USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/create", "/course").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/course/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> basic.authenticationEntryPoint(customAuthEntryPoint));
        return http.build();
    }

}
