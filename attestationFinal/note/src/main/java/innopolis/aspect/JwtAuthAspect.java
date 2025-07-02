package innopolis.aspect;

import innopolis.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Aspect
@Component
@RequiredArgsConstructor
public class JwtAuthAspect {
    private final JwtService jwtService;

    /**
     * Метод перехватывает контекст запроса, забирает токен авторизации, проверяет и извлекает из него userId
     * помещает в этот же запрос как атрибут userId
     */
    @Before("@annotation(JwtAuth)")
    public void validateToken() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();

        var header = request.getHeader(AUTHORIZATION);

        // Проверяем наличие и формат заголовка до попытки разбора
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token missing");
        }

        var token = header.substring(7);
        if (jwtService.isTokenExpired(token)) {
            throw new RuntimeException("Token expired");
        }

        request.setAttribute("userId", jwtService.extractUserId(token));
    }
}
