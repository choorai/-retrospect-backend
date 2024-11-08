package choorai.retrospect.card.entity.repository;

import choorai.retrospect.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
