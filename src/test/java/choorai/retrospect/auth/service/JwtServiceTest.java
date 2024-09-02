package choorai.retrospect.auth.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService("mytestsecretkeymytestsecretkeymytestsecretkey");

    @Test
    @DisplayName("유효한 access Token을 생성할 수 있다.")
    void generateAccessTokenTest() {
        // given
        String email = "test@example.com";
        // when
        String token = jwtService.generateAccessToken(email);
        // then
        assertTrue(jwtService.validateToken(token));
        assertEquals(email, jwtService.extractUserEmail(token));
    }

    @Test
    @DisplayName("유효한 refresh Token을 생성할 수 있다.")
    void generateRefreshTokenTest() {
        // given
        String email = "test@example.com";
        // when
        String token = jwtService.generateRefreshToken(email);
        // then
        assertTrue(jwtService.validateToken(token));
    }

    @Test
    @DisplayName("생성 된 토큰에서 userEmail을 추출할 수 있다.")
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

    @Test
    @DisplayName("생성 된 토큰에서 expiration을 추출할 수 있다.")
    void extractExpirationTest() {
        // given
        String email = "test@example.com";
        String token = jwtService.generateAccessToken(email);
        // when
        final Date expiration = jwtService.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    @DisplayName("유효하지 않은 token의 validate() 결과는 false이다.")
    void invalidTokenTest() {
        String malformedToken = "malformedToken";

        // When
        boolean isValid = jwtService.validateToken(malformedToken);

        // Then
        assertFalse(isValid);
    }

}
