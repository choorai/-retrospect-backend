package choorai.retrospect.retrospect_room.card.exception;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;

import java.util.Map;

public class CardException extends CommonException {

    public CardException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public CardException(final ErrorCode errorCode, final Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
