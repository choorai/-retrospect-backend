package choorai.retrospect.retrospect_room.card.entity.value;

import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;

public class Content {

    private String value;

    public Content(final String value) {
        validateContent(value);
        this.value = value;
    }

    private void validateContent(final String content) {
        if (content == null || content.isEmpty()) {
            throw new CardException(CardErrorCode.CONTENT_IS_NOT_NULL);
        }
    }

}
