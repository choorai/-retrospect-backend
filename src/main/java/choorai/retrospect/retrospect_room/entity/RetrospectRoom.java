package choorai.retrospect.retrospect_room.entity;

import choorai.retrospect.card.entity.Card;
import choorai.retrospect.card.exception.CardErrorCode;
import choorai.retrospect.card.exception.CardException;
import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.retrospect_room.entity.value.Details;
import choorai.retrospect.retrospect_room.entity.value.ShareLink;
import choorai.retrospect.retrospect_room.entity.value.Subject;
import choorai.retrospect.retrospect_room.entity.value.Type;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Time;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RetrospectRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Subject subject;

    @Embedded
    private Details details;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Time timeLimit;

    @Embedded
    private ShareLink shareLink;

    @ToString.Exclude
    @OneToMany(mappedBy = "retrospectRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCardById(Long cardId) {
        boolean removed = cards.removeIf(card -> card.getId().equals(cardId));
        if (!removed) {
            throw new CardException(CardErrorCode.CARD_NOT_FOUND_FOR_ID);
        }
    }
}
