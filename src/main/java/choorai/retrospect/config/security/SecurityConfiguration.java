package choorai.retrospect.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain healthCheckFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher(antMatcher("/actuator/**"))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(antMatcher("/actuator/health")).permitAll();
                auth.anyRequest().authenticated();
            })
            .build();
    }
}
