package choorai.retrospect.auth.exception;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;
import java.util.Map;

public class AuthException extends CommonException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(ErrorCode errorCode,
                         Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
