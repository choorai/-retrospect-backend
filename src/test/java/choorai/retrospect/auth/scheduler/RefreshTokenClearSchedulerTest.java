package choorai.retrospect.auth.scheduler;

import choorai.retrospect.auth.entity.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.verify;

class RefreshTokenClearSchedulerTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenClearScheduler refreshTokenClearScheduler;

    @DisplayName("토큰 scheduler 동작 여부를 테스트한다.")
    @Test
    public void testCleanUpExpiredTokens() {
        // given
        MockitoAnnotations.openMocks(this);
        Date now = new Date();
        // when
        refreshTokenClearScheduler.cleanUpExpiredTokens();
        // then
        verify(refreshTokenRepository).deleteByExpiryDateBefore(now);
    }
}
