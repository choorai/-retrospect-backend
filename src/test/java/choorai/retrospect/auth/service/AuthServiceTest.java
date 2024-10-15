package choorai.retrospect.auth.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import choorai.retrospect.auth.entity.RefreshToken;
import choorai.retrospect.auth.entity.dto.LoginRequest;
import choorai.retrospect.auth.entity.dto.LoginResponse;
import choorai.retrospect.auth.entity.dto.ReissueTokenResponse;
import choorai.retrospect.auth.entity.repository.RefreshTokenRepository;
import choorai.retrospect.auth.exception.AuthErrorCode;
import choorai.retrospect.auth.exception.AuthException;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.entity.value.Email;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    /*
        뭐 어떻게 바꿔야하나? SpringBootTest만 붙이고 -> service는 다 @Autowired로?
        그럼 Repository 들은 어짜나 얘넨 목으로 냅두나?
        jwtService에서 임시로 토큰 만드는 부분 있잖아 (-> authService에서 생성되는거)
        -> 그건 테스트 어떻게 하나?
     */


    @Autowired
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    RefreshTokenRepository refreshTokenRepository;


    JwtService jwtService;

    private User user;
    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        user = new User("alswn12345@gmail.com", "alswn12345", "정민주");
        refreshToken = new RefreshToken("refreshTokenValue", user,
                                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
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
            .isInstanceOf(AuthException.class)
            .hasMessage(AuthErrorCode.USER_NOT_FOUND.getMessage());
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
            .isInstanceOf(AuthException.class)
            .hasMessage(AuthErrorCode.WRONG_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("올바른 이메일과 비밀번호를 입력하면 access Token과 refresh Token이 반환된다.")
    void loginSuccessTest() {
        // given
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("alswn12345@gmail.com");
        loginRequest.setPassword("alswn12345");

        // when
        when(jwtService.generateAccessToken(anyString())).thenReturn("testAccessToken");
        when(jwtService.generateRefreshToken(anyString())).thenReturn("testRefreshToken");
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        final LoginResponse loginResponse = authService.login(loginRequest);

        // then
        assertAll(
            () -> assertEquals(loginResponse.getAccessToken(), "testAccessToken"),
            () -> assertEquals(loginResponse.getRefreshToken(), "testRefreshToken")
        );
    }

    @Test
    @DisplayName("유효한 리프레시 토큰으로 refreshAccessToken을 요청하면 새로운 access token이 반환된다.")
    void refreshAccessTokenSuccessTest() {
        // given
        String refreshTokenValue = "refreshTokenValue";

        // when
        when(jwtService.validateToken(anyString())).thenReturn(true);
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refreshToken));
        when(jwtService.extractUserEmail(refreshTokenValue)).thenReturn("alswn12345@gmail.com");
        when(jwtService.generateAccessToken(anyString())).thenReturn("newAccessToken");

        // then
        ReissueTokenResponse reissueTokenResponse = authService.reissueAccessToken(refreshTokenValue);
        assertEquals("newAccessToken", reissueTokenResponse.getAccessToken());
    }

    @Test
    @DisplayName("유효하지 않은 리프레시 토큰으로 예외가 발생한다.")
    void refreshAccessTokenInvalidTokenTest() {
        // given
        String refreshTokenValue = "invalidToken";

        // when
        when(jwtService.validateToken(refreshTokenValue)).thenReturn(false);

        // then
        assertThatThrownBy(() -> authService.reissueAccessToken(refreshTokenValue))
            .isInstanceOf(AuthException.class)
            .hasMessage(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage());
    }

    @Test
    @DisplayName("만료된 리프레시 토큰으로 예외가 발생한다.")
    void refreshAccessTokenExpiredTokenTest() {
        // given
        String refreshTokenValue = "expiredToken";
        RefreshToken expiredToken = new RefreshToken(refreshTokenValue, user,
                                                     new Date(System.currentTimeMillis() - 10000));

        // when
        when(jwtService.validateToken(anyString())).thenReturn(true);
        when(refreshTokenRepository.findByToken(refreshTokenValue)).thenReturn(Optional.of(expiredToken));

        // then
        assertThatThrownBy(() -> authService.reissueAccessToken(refreshTokenValue))
            .isInstanceOf(AuthException.class)
            .hasMessage(AuthErrorCode.REFRESH_TOKEN_IS_EXPIRED.getMessage());
    }

    @Test
    @DisplayName("로그아웃 시 리프레시 토큰이 삭제된다.")
    void logoutSuccessTest() {
        // given
        String refreshTokenValue = "refreshTokenValue";

        // when
        authService.logout(refreshTokenValue);

        // then
        Mockito.verify(refreshTokenRepository, times(1))
            .deleteByToken(refreshTokenValue);
    }

}
