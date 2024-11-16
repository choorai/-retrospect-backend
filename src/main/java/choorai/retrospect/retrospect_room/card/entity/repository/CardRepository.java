package choorai.retrospect.retrospect_room.card.entity.repository;


import choorai.retrospect.retrospect_room.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
