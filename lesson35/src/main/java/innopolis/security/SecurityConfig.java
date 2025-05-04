package innopolis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Настройка параметров безопасности.
 */
@Configuration
public class SecurityConfig {

    /**
     * Добавление пользователей и добавление ролей.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        var user = User.withUsername("user")
                .password("{noop}qwerty")
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password("{noop}qwerty")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * Метод для настройки доступов к endpoint для определенных ролей
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers("/swagger-ui/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/order/*").hasRole("USER")
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
