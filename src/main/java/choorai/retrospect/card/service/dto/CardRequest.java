package choorai.retrospect.card.service.dto;

import choorai.retrospect.card.entity.value.Type;
import choorai.retrospect.card.exception.CardErrorCode;
import choorai.retrospect.card.exception.CardException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequest {

    private String type;
    private String content;

    public CardRequest(String type, String content) {
        validate(type, content);
        this.type = type;
        this.content = content;
    }

    private void validate(String type, String content) {
        validateType(type);
        validateContent(content);
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
