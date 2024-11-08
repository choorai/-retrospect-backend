package choorai.retrospect.card.exception;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;
import java.util.Map;

public class CardException extends CommonException {

    public CardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardException(ErrorCode errorCode, Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
