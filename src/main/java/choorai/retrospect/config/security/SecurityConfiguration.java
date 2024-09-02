package choorai.retrospect.config.security;


import choorai.retrospect.auth.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher(antMatcher("/actuator/**"))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(antMatcher("/actuator/health")).permitAll();
                auth.anyRequest().authenticated();
            })
            .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 필터 추가
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(antMatcher("/auth/**")).permitAll();
                auth.anyRequest().authenticated();
            })
            .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain corsFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(corsConfig -> {
                corsConfig.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedMethods(List.of("POST", "GET", "PUT", "PATCH", "DELETE"));
                    // TODO : 배포후 연동 시 변경
                    configuration.addAllowedOrigin("*");
                    configuration.addAllowedHeader("*");
                    return configuration;
                });
            })
            .build();
    }
}
