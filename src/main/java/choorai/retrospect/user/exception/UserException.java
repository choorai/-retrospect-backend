package choorai.retrospect.user.exception;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;

import java.util.Map;

public class UserException extends CommonException {

    public UserException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(final ErrorCode errorCode,
                         final Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
