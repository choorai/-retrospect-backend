package choorai.retrospect.retrospect_room.card.service.dto;

import choorai.retrospect.retrospect_room.card.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardResponse {

    private final long id;
    private final String type;
    private final String content;

    public static CardResponse of(final Card card) {
        return new CardResponse(card.getId(), card.getType().toString(), card.getContent());
    }

}
