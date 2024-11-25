package choorai.retrospect.retrospect_room.excpetion;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;

import java.util.Map;

public class RetrospectRoomException extends CommonException {

    public RetrospectRoomException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public RetrospectRoomException(final ErrorCode errorCode, final Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
