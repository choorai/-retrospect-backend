package choorai.retrospect.retrospect_room.card.exception;

import choorai.retrospect.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CardErrorCode implements ErrorCode {

    CONTENT_IS_NOT_NULL(HttpStatus.BAD_REQUEST, 4001, "content는 필수값 입니다."),
    TYPE_IS_NOT_NULL(HttpStatus.BAD_REQUEST, 4002, "type은 필수값 입니다."),
    INVALID_TYPE(HttpStatus.BAD_REQUEST, 4003, "type은 KEEP, PROBLEM, TRY 중 하나여야 합니다."),
    RETROSPECT_ROOM_IS_NOT_NULL(HttpStatus.BAD_REQUEST, 4004, "retrospectRoom은 필수값입니다."),
    USER_IS_NOT_NULL(HttpStatus.BAD_REQUEST, 4005, "user은 필수값입니다."),
    CARD_NOT_FOUND_FOR_ID(HttpStatus.BAD_REQUEST, 4006, "동일한 id를 가진 card를 찾을 수 없습니다."),
    CARD_IS_NOT_IN_ROOM(HttpStatus.BAD_REQUEST, 4007, "주어진 id의 카드는 해당 회고룸에 존재하지 않습니다.");;


    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    CardErrorCode(final HttpStatus httpStatus, final int errorCode, final String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
