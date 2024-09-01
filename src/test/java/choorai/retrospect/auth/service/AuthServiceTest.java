package choorai.retrospect.auth.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import choorai.retrospect.user.entity.User;
import choorai.retrospect.auth.entity.dto.LoginRequest;
import choorai.retrospect.auth.entity.dto.LoginResponse;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.entity.value.Email;
import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthService authService;

    @Test
    @DisplayName(value = "존재하는 이메일과 올바른 비밀번호를 입력하면 로그인에 성공한다.")
    void successTest() {
        // given
        final User user = new User("alswn12345@gmail.com", "alswn12345", "정민주");
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("alswn12345@gmail.com");
        loginRequest.setPassword("alswn12345");
        // when
        when(userRepository.findByEmail(eq(new Email(loginRequest.getEmail()))))
            .thenReturn(Optional.of(user));
        final LoginResponse loginResponse = authService.login(loginRequest);
        // then
        assertNotNull(loginResponse);
    }

    @Test
    @DisplayName(value = "존재하지 않은 이메일을 입력하면 예외가 발생한다.")
    void notFoundUserTest() {
        // given
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("alswn12345@gmail.com");
        loginRequest.setPassword("alswn12345");
        // when
        when(userRepository.findByEmail(eq(new Email(loginRequest.getEmail()))))
            .thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> authService.login(loginRequest))
            .isInstanceOf(UserException.class)
            .hasMessage(UserErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName(value = "틀린 비밀번호를 입력하면 예외가 발생한다.")
    void wrongPasswordTest() {
        // given
        final User user = new User("alswn12345@gmail.com", "alswn12345", "정민주");
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("alswn12345@gmail.com");
        loginRequest.setPassword("alswn1111");
        // when
        when(userRepository.findByEmail(eq(new Email(loginRequest.getEmail()))))
            .thenReturn(Optional.of(user));
        // then
        assertThatThrownBy(() -> authService.login(loginRequest))
            .isInstanceOf(UserException.class)
            .hasMessage(UserErrorCode.WRONG_PASSWORD.getMessage());
    }

}
