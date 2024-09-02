package choorai.retrospect.auth.service;

import choorai.retrospect.auth.entity.RefreshToken;
import choorai.retrospect.auth.entity.repository.RefreshTokenRepository;
import choorai.retrospect.auth.exception.AuthErrorCode;
import choorai.retrospect.auth.exception.AuthException;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.auth.entity.dto.LoginRequest;
import choorai.retrospect.auth.entity.dto.LoginResponse;
import choorai.retrospect.auth.entity.dto.ReissueTokenResponse;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.entity.value.Email;
import jakarta.transaction.Transactional;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;


    @Transactional
    public LoginResponse login(LoginRequest request) {
        final User findUser = getUser(request);
        if (!findUser.getPassword().isEqual(request.getPassword())) {
            throw new AuthException(AuthErrorCode.WRONG_PASSWORD);
        }
        final String accessToken = jwtService.generateAccessToken(request.getEmail());
        final String refreshToken = jwtService.generateRefreshToken(request.getEmail());
        saveRefreshToken(refreshToken, findUser);
        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public ReissueTokenResponse reissueAccessToken(String refreshTokenValue) {
        if (!jwtService.validateToken(refreshTokenValue)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
        final RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
            .orElseThrow(() -> new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));
        if (refreshToken.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }

        final String userEmail = jwtService.extractUserEmail(refreshTokenValue);
        final String newAccessToken = jwtService.generateAccessToken(userEmail);
        return new ReissueTokenResponse(newAccessToken);
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        refreshTokenRepository.deleteByToken(refreshTokenValue);
    }

    private void saveRefreshToken(String refreshTokenValue, User user) {
        Date expiryDate = jwtService.extractExpiration(refreshTokenValue);
        RefreshToken refreshToken = new RefreshToken(refreshTokenValue, user, expiryDate);
        refreshTokenRepository.save(refreshToken);
    }

    private User getUser(LoginRequest request) {
        final Email inputEmail = new Email(request.getEmail());
        return userRepository.findByEmail(inputEmail)
            .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));
    }
}
