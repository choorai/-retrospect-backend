package choorai.retrospect.user.entity.value;

import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 255;
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");


    @Column(name = "email")
    private String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String inputValue) {
        validateByteLength(inputValue);
        validateForm(inputValue);
    }

    private void validateByteLength(String inputValue) {
        final int inputValueByteLength = inputValue.getBytes(StandardCharsets.UTF_8).length;
        if (inputValueByteLength < MIN_LENGTH || inputValueByteLength > MAX_LENGTH) {
            throw new UserException(UserErrorCode.EMAIL_LENGTH_ERROR);
        }
    }

    private void validateForm(String inputValue) {
        Matcher matcher = EMAIL_PATTERN.matcher(inputValue);
        if (!matcher.matches()) {
            throw new UserException(UserErrorCode.EMAIL_FORM_ERROR);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
