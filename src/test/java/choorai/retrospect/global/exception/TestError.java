package choorai.retrospect.global.exception;

import org.springframework.http.HttpStatus;

public enum TestError implements ErrorCode {

    TEST_ERROR_CODE(HttpStatus.BAD_REQUEST, 1000, "잘못된 에러 입력");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    TestError(final HttpStatus httpStatus, final int errorCode, final String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
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
