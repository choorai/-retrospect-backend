package choorai.retrospect.user.exception;

import choorai.retrospect.global.exception.ErrorCode;
import choorai.retrospect.user.entity.value.Email;
import choorai.retrospect.user.entity.value.Name;
import choorai.retrospect.user.entity.value.Password;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    EMAIL_FORM_ERROR(HttpStatus.BAD_REQUEST, 1001, "이메일 형식이 아닙니다."),
    EMAIL_LENGTH_ERROR(HttpStatus.BAD_REQUEST, 1002,
                       String.format("이메일은 %d ~ %d자 사이여야합니다.", Email.MIN_LENGTH, Email.MAX_LENGTH)),
    PASSWORD_LENGTH_ERROR(HttpStatus.BAD_REQUEST, 1003,
                      String.format("비밀번호는 %d ~ %d자 사이여야 합니다.", Password.MIN_LENGTH, Password.MAX_LENGTH)),
    NAME_LENGTH_ERROR(HttpStatus.BAD_REQUEST, 1004,
                      String.format("이름은 %d ~ %d자 사이여야 합니다.", Name.MIN_LENGTH, Name.MAX_LENGTH)),
    USER_NOT_FOUND_FOR_ID(HttpStatus.BAD_REQUEST, 1005, "동일한 id를 가진 User를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    UserErrorCode(final HttpStatus httpStatus, final int errorCode, final String errorMessage) {
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
