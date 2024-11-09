package choorai.retrospect.card.entity.dto;

import choorai.retrospect.card.entity.Card;
import lombok.Getter;

@Getter
public class CardResponse {

    private final long id;
    private final String type;
    private final String content;

    private CardResponse(long id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public static CardResponse of(Card card) {
        return new CardResponse(card.getId(), card.getType().toString(), card.getContent());
    }
}
