package choorai.retrospect.auth.ui;

import choorai.retrospect.auth.entity.dto.LoginRequest;
import choorai.retrospect.auth.entity.dto.LoginResponse;
import choorai.retrospect.auth.entity.dto.LogoutRequest;
import choorai.retrospect.auth.entity.dto.RefreshTokenRequest;
import choorai.retrospect.auth.entity.dto.RefreshTokenResponse;
import choorai.retrospect.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        final LoginResponse loginResponse = userService.login(request);
        return ResponseEntity.ok()
            .body(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody final RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        final RefreshTokenResponse refreshTokenResponse = userService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok()
            .body(refreshTokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody final LogoutRequest request) {
        final String refreshToken = request.getRefreshToken();
        userService.logout(refreshToken);
        return ResponseEntity.ok()
            .body("LOGOUT SUCCESS");
    }

}
