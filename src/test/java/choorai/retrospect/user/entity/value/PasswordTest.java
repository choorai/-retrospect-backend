package choorai.retrospect.user.entity.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @DisplayName("비밀번호를 입력받아서 Password 객체를 만들 수 있다.")
    @Test
    void successCreateTest() {
        // given
        String inputValue = "afjdkljfaldk1123";
        // when
        final Password password = new Password(inputValue);
        // then
        assertThat(password.getValue())
            .isEqualTo(inputValue);
    }

    @ParameterizedTest(name = "{0}은 정해진 비밀번호 길이를 벗어나므로 예외가 발생한다.")
    @ValueSource(strings = {"", "abcd"})
    void lengthErrorTest(String inputValue) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Password(inputValue))
            .isInstanceOf(UserException.class)
            .hasMessage(UserErrorCode.PASSWORD_LENGTH_ERROR.getMessage());
    }

}
