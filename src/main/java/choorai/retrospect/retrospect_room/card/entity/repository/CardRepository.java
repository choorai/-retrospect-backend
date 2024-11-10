package choorai.retrospect.retrospect_room.card.entity.repository;

import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public class CardRepository {

    public interface RetrospectRoomRepository extends JpaRepository<RetrospectRoom, Long> {

        @EntityGraph(attributePaths = {"cards"})
        Optional<RetrospectRoom> findWithCardsById(Long userId);
    }

}
