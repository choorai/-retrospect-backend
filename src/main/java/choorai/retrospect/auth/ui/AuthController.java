package choorai.retrospect.auth.ui;

import choorai.retrospect.auth.entity.dto.LoginRequest;
import choorai.retrospect.auth.entity.dto.LoginResponse;
import choorai.retrospect.auth.entity.dto.LogoutRequest;
import choorai.retrospect.auth.entity.dto.ReissueTokenRequest;
import choorai.retrospect.auth.entity.dto.ReissueTokenResponse;
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

    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokenResponse> reissue(@RequestBody final ReissueTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        final ReissueTokenResponse reissueTokenResponse = userService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok()
            .body(reissueTokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody final LogoutRequest request) {
        final String refreshToken = request.getRefreshToken();
        userService.logout(refreshToken);
        return ResponseEntity.ok()
            .body("LOGOUT SUCCESS");
    }

}
