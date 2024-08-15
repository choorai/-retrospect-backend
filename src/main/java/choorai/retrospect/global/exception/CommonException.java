package choorai.retrospect.global.exception;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private static final String PROPERTY_VALUE = "Key: %s, Value: %s";
    private static final String VALUE_DELIMITER = " // ";

    private final ErrorCode errorCode;
    private final Map<String, Object> additionalInfo;

    public CommonException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.additionalInfo = null;
    }

    public CommonException(final ErrorCode errorCode, Map<String, Object> additionalInfo) {
        this.errorCode = errorCode;
        this.additionalInfo = additionalInfo;
    }

    public String getInputValue() {
        return Objects.requireNonNull(additionalInfo).entrySet()
            .stream()
            .map(entry -> String.format(PROPERTY_VALUE, entry.getKey(), entry.getValue().toString()))
            .collect(Collectors.joining(VALUE_DELIMITER));
    }

    public String getCode() {
        return errorCode.getCode();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public boolean isInputFieldsNUll() {
        return additionalInfo == null;
    }
}
