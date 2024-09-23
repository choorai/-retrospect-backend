package choorai.retrospect.config;

import choorai.retrospect.auth.exception.AuthErrorCode;
import choorai.retrospect.auth.exception.AuthException;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.entity.value.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(new Email(username))
            .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));
    }
}
