package choorai.retrospect.user.entity.value;

import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 32;
    private static final Pattern EMAIL_PATTERN
        = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");


    @Column(name = "email")
    private String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String inputValue) {
        validateByteLength(inputValue);
        validateForm(inputValue);
    }

    private void validateByteLength(final String inputValue) {
        if (inputValue.length() < MIN_LENGTH || inputValue.length() > MAX_LENGTH) {
            throw new UserException(UserErrorCode.EMAIL_LENGTH_ERROR);
        }
    }

    private void validateForm(final String inputValue) {
        Matcher matcher = EMAIL_PATTERN.matcher(inputValue);
        if (!matcher.matches()) {
            throw new UserException(UserErrorCode.EMAIL_FORM_ERROR);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
