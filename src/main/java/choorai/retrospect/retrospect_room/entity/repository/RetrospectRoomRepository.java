package choorai.retrospect.retrospect_room.entity.repository;

import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetrospectRoomRepository extends JpaRepository<RetrospectRoom, Long> {

    @EntityGraph(attributePaths = {"cards"})
    Optional<RetrospectRoom> findWithCardsById(final Long userId);
}
