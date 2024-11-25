package choorai.retrospect.global.exception;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class CommonException extends RuntimeException {

    private static final String FORMAT_OF_EXCEPTION = "%s : %s";
    private static final String PREFIX_OF_EXCEPTION = "{";
    private static final String DELIMITER_OF_EXCEPTION = ", ";
    private static final String SUFFIX_OF_EXCEPTION = "}";

    private final ErrorCode errorCode;
    private final Map<String, Object> additionalInfo;

    public CommonException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.additionalInfo = null;
    }

    public CommonException(final ErrorCode errorCode, final Map<String, Object> additionalInfo) {
        this.errorCode = errorCode;
        this.additionalInfo = additionalInfo;
    }

    public String getInputValue() {
        return Objects.requireNonNull(additionalInfo).entrySet()
            .stream()
            .map(entry -> String.format(FORMAT_OF_EXCEPTION, entry.getKey(), entry.getValue().toString()))
            .collect(Collectors.joining(DELIMITER_OF_EXCEPTION, PREFIX_OF_EXCEPTION, SUFFIX_OF_EXCEPTION));
    }

    public int getCode() {
        return errorCode.getCode();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public boolean isInputFieldsNUll() {
        return additionalInfo == null;
    }
}
