package choorai.retrospect.user.entity.value;

import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    public static final int MIN_LENGTH = 5;
    public static final int MAX_LENGTH = 255;

    @Column(name = "password")
    private String value;

    public Password(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String inputValue) {
        validateByteLength(inputValue);
    }

    private void validateByteLength(String inputValue) {
        if (inputValue.length() < MIN_LENGTH || inputValue.length() > MAX_LENGTH) {
            throw new UserException(UserErrorCode.PASSWORD_LENGTH_ERROR);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
