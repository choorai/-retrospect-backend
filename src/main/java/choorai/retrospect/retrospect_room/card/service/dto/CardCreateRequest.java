package choorai.retrospect.retrospect_room.card.service.dto;

import choorai.retrospect.retrospect_room.card.entity.value.Type;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import lombok.Getter;

@Getter
public class CardCreateRequest {

    private String type;
    private String content;

    public CardCreateRequest(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public void validate() {
        validateType(this.type);
        validateContent(this.content);
    }

    private void validateType(String type) throws CardException {
        if (type == null || type.isEmpty()) {
            throw new CardException(CardErrorCode.TYPE_IS_NOT_NULL);
        }
        if (!Type.isValidKey(type)) {
            throw new CardException(CardErrorCode.INVALID_TYPE);
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new CardException(CardErrorCode.CONTENT_IS_NOT_NULL);
        }
    }

}
