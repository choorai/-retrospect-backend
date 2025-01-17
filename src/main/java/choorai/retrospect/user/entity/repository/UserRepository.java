package choorai.retrospect.user.entity.repository;

import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.value.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Email email);

    @EntityGraph(attributePaths = {"cards"})
    Optional<User> findWithCardsById(Long userId);

}
