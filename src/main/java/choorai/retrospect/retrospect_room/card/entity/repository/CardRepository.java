package choorai.retrospect.retrospect_room.card.entity.repository;


import choorai.retrospect.retrospect_room.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByRetrospectRoom_Id(Long retrospectRoomId);

}
