package choorai.retrospect.user.exception;

import choorai.retrospect.global.exception.ErrorCode;
import choorai.retrospect.user.entity.value.Email;
import choorai.retrospect.user.entity.value.Name;
import choorai.retrospect.user.entity.value.Password;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    EMAIL_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "잘못된 입력",
                       String.format("이메일은 %d ~ %d 바이트 사이여야합니다.", Email.MIN_LENGTH, Email.MAX_LENGTH)),
    EMAIL_FORM_ERROR(HttpStatus.BAD_REQUEST, "잘못된 입력", "이메일 형식이 아닙니다."),
    PASSWORD_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "잘못된 입력",
                      String.format("비밀번호는 %d ~ %d 바이트 사이여야 합니다.", Password.MIN_LENGTH, Password.MAX_LENGTH)),
    NAME_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "잘못된 입력",
                      String.format("이름은 %d ~ %d 바이트 사이여야 합니다.", Name.MIN_LENGTH, Name.MAX_LENGTH));
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    UserErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
