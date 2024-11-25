package choorai.retrospect.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService("mytestsecretkeymytestsecretkeymytestsecretkey");

    @DisplayName("유효한 access Token을 생성할 수 있다.")
    @Test
    void generateAccessTokenTest() {
        // given
        String email = "test@example.com";
        // when
        String token = jwtService.generateAccessToken(email);
        // then
        assertTrue(jwtService.validateToken(token));
        assertEquals(email, jwtService.extractUserEmail(token));
    }

    @DisplayName("유효한 refresh Token을 생성할 수 있다.")
    @Test
    void generateRefreshTokenTest() {
        // given
        String email = "test@example.com";
        // when
        String token = jwtService.generateRefreshToken(email);
        // then
        assertTrue(jwtService.validateToken(token));
    }

    @DisplayName("생성 된 토큰에서 userEmail을 추출할 수 있다.")
    @Test
    void extractEmailTest() {
        // given
        String email = "test@example.com";
        String token = jwtService.generateAccessToken(email);
        // when
        final String extractedUserEmail = jwtService.extractUserEmail(token);
        // then
        assertThat(extractedUserEmail)
            .isEqualTo(email);
    }

    @DisplayName("생성 된 토큰에서 expiration을 추출할 수 있다.")
    @Test
    void extractExpirationTest() {
        // given
        String email = "test@example.com";
        String token = jwtService.generateAccessToken(email);
        // when
        final Date expiration = jwtService.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @DisplayName("유효하지 않은 token의 validate() 결과는 false이다.")
    @Test
    void invalidTokenTest() {
        String malformedToken = "malformedToken";

        // When
        boolean isValid = jwtService.validateToken(malformedToken);

        // Then
        assertFalse(isValid);
    }
}
