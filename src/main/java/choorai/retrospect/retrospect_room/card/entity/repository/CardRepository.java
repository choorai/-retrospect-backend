package choorai.retrospect.retrospect_room.card.entity.repository;


import choorai.retrospect.retrospect_room.card.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByRetrospectRoom_Id(Long retrospectRoomId);

}
