package choorai.retrospect.auth.scheduler;

import choorai.retrospect.auth.entity.repository.RefreshTokenRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefreshTokenClearScheduler {

    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanUpExpiredTokens() {
        refreshTokenRepository.deleteByExpiryDateBefore(new Date());
    }

}
