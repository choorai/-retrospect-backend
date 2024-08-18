package choorai.retrospect.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

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
