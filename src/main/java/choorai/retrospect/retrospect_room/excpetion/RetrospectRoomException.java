package choorai.retrospect.retrospect_room.excpetion;

import choorai.retrospect.global.exception.CommonException;
import choorai.retrospect.global.exception.ErrorCode;

import java.util.Map;

public class RetrospectRoomException extends CommonException {

    public RetrospectRoomException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RetrospectRoomException(ErrorCode errorCode, Map<String, Object> additionalInfo) {
        super(errorCode, additionalInfo);
    }
}
