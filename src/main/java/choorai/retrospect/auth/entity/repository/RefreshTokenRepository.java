package choorai.retrospect.auth.entity.repository;

import choorai.retrospect.auth.entity.RefreshToken;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    
    void deleteByToken(String token);

    void deleteByExpiryDateBefore(Date now);
}
