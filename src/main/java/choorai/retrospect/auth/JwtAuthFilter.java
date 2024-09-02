package choorai.retrospect.auth;

import choorai.retrospect.auth.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthFilter implements Filter {

    private final JwtService jwtService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        if (requestURI.startsWith("/auth/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtService.validateToken(token)) {
                    String userEmail = jwtService.extractUserEmail(token);
                    httpRequest.setAttribute("userEmail", userEmail);
                } else {
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT가 유효하지 않습니다.");
                    return;
                }
            } catch (JwtException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 검증 작업 중 예외가 발생했습니다.");
                return;
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증을 위한 헤더가 존재하지 않습니다.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
