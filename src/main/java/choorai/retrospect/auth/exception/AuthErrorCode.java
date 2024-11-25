package choorai.retrospect.auth.exception;

import choorai.retrospect.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, 2001, "아이디 또는 비밀번호가 잘못됐습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 2002, "아이디 또는 비밀번호가 잘못됐습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, 2003, "refresh token이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, 2004, "refresh token이 존재하지 않습니다."),
    REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST, 2005, "refresh token이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    AuthErrorCode(final HttpStatus httpStatus, final int errorCode, final String errorMessage) {
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
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
