package choorai.retrospect.retrospect_room.card.service.dto;

import choorai.retrospect.retrospect_room.card.entity.Card;
import lombok.Getter;

@Getter
public class CardResponse {

    private final long id;
    private final String type;
    private final String content;

    public CardResponse(long id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public static CardResponse of(Card card) {
        return new CardResponse(card.getId(), card.getType().toString(), card.getContent());
    }
}
