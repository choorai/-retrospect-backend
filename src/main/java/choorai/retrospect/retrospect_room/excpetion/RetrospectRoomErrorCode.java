package choorai.retrospect.retrospect_room.excpetion;

import choorai.retrospect.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum RetrospectRoomErrorCode implements ErrorCode {

    // TODO : errorCode는 추후 회의를 통해 숫자로 변경
    SUBJECT_LENGTH_ERROR(HttpStatus.BAD_REQUEST, 3001, "회고 주제의 글자 길이는 200자를 넘길 수 없습니다."),
    DETAILS_LENGTH_ERROR(HttpStatus.BAD_REQUEST, 3002, "회고 상세 내용은 500자 미만이어야 합니다."),
    SHARE_LINK_ERROR(HttpStatus.BAD_REQUEST, 3003, "공유 링크 크기는 200자를 넘길 수 없습니다"),
    RETROSPECT_ROOM_NOT_FOUND_FOR_ID(HttpStatus.BAD_REQUEST, 3004, "동일한 id를 가진 retrospectRoom을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    RetrospectRoomErrorCode(final HttpStatus httpStatus, final int errorCode, final String errorMessage) {
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
