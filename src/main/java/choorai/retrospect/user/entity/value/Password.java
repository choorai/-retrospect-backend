package choorai.retrospect.user.entity.value;

import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import java.nio.charset.StandardCharsets;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    public static final int MIN_LENGTH = 5;
    public static final int MAX_LENGTH = 255;

    @Column(name = "password")
    private String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String inputValue) {
        validateByteLength(inputValue);
    }

    private void validateByteLength(String inputValue) {
        final int inputValueByteLength = inputValue.getBytes(StandardCharsets.UTF_8).length;
        if (inputValueByteLength < MIN_LENGTH || inputValueByteLength > MAX_LENGTH) {
            throw new UserException(UserErrorCode.PASSWORD_LENGTH_ERROR);
        }
    }

    public boolean isEqual(String value) {
        return this.value.equals(value);
    }

}
