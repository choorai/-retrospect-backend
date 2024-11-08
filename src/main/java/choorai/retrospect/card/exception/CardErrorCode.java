package choorai.retrospect.card.exception;

import choorai.retrospect.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CardErrorCode implements ErrorCode {

    CONTENT_IS_NOT_NULL(HttpStatus.BAD_REQUEST, 4001, "content는 필수값 입니다."),
    TYPE_IS_NOT_NULL(HttpStatus.BAD_REQUEST, 4002, "type은 필수값 입니다."),
    CARD_NOT_FOUND_FOR_ID(HttpStatus.BAD_REQUEST, 4003, "동일한 id를 가진 card를 찾을 수 없습니다.");



    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    CardErrorCode(HttpStatus httpStatus, int errorCode, String errorMessage) {
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
        return this.getCode();
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }
}
