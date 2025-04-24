package innopolis.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestControllerAdvice
public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        var header = request.getHeader("Authorization");
        log.info("Input request: {} {}, Authorization header {}", request.getMethod(), request.getRequestURI(), header);
        log.info("decode header: {}", new String (Base64.getUrlDecoder().decode(header.replace("Basic ", ""))));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
