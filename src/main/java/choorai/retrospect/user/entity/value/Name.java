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
public class Name {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 20;

    @Column(name = "name")
    private String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String inputValue) {
        validateByteLength(inputValue);
    }

    private void validateByteLength(String inputValue) {
        if (inputValue.isEmpty() || inputValue.length()> MAX_LENGTH) {
            throw new UserException(UserErrorCode.NAME_LENGTH_ERROR);
        }
    }
}
