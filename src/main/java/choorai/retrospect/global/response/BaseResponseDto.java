package choorai.retrospect.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class BaseResponseDto<D> {

    private final ResultCode resultCode;
    private final D data;


    public static <D> BaseResponseDto<D> ofSuccess() {
        return new BaseResponseDto<>(ResultCode.SUCCESS, null);
    }

    public static <D> BaseResponseDto<D> ofSuccess(D data) {
        return new BaseResponseDto<>(ResultCode.SUCCESS, data);
    }

    public static <Exception> BaseResponseDto<Exception> ofException(Exception e) {
        return new BaseResponseDto<>(ResultCode.FAIL, e);
    }

    public static <D> BaseResponseDto<D> ofFail() {
        return new BaseResponseDto<>(ResultCode.FAIL, null);
    }

}
