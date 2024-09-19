package choorai.retrospect.auth.scheduler;

import static org.mockito.Mockito.verify;

import choorai.retrospect.auth.entity.repository.RefreshTokenRepository;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RefreshTokenClearSchedulerTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenClearScheduler refreshTokenClearScheduler;

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
