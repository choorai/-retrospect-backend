package choorai.retrospect.user.entity.repository;

import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.value.Email;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(final Email email);

    @EntityGraph(attributePaths = {"cards"})
    Optional<User> findWithCardsById(final Long userId);

}
