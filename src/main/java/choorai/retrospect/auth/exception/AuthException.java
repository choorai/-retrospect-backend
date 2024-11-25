package choorai.retrospect.auth.exception;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;

import java.util.Map;

public class AuthException extends CommonException {

    public AuthException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(final ErrorCode errorCode,
                         final Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
