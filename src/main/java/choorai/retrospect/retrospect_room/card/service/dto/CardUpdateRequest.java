package choorai.retrospect.retrospect_room.card.service.dto;

import choorai.retrospect.retrospect_room.card.entity.value.Type;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import lombok.Getter;

@Getter
public class CardUpdateRequest {

    private String type;
    private String content;

    public CardUpdateRequest(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public void validate() {
        validateType(this.type);
    }

    private void validateType(String type) throws CardException {
        if (type == null || type.isEmpty()) {
            return;
        }
        if (!Type.isValidKey(type)) {
            throw new CardException(CardErrorCode.INVALID_TYPE);
        }
    }

}
