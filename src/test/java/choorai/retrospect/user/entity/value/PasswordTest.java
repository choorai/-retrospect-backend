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

    @Test
    @DisplayName("비밀번호를 입력받아서 Password 객체를 만들 수 있다.")
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
    @ValueSource(strings = {"", "abcd", "이 문장은 한글과 영문, 특수 문자가 혼합되어 있습니다. 영어 단어도 포함됩니다: example. 숫자와 특수 기호: 1234567890!@#$%^&*()_+[]{}|;:',.<>? 이 텍스트는 문자열의 길이를 증가시키기 위해 작성되었습니다. 또 다른 문장을 추가합니다: 이 문장은 255 바이트를 초과하는 예시입니다. 이 문장을 255 바이트를 초과하는 예시입니다."})
    void lengthErrorTest(String inputValue) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Password(inputValue))
            .isInstanceOf(UserException.class)
            .hasMessage(UserErrorCode.PASSWORD_LENGTH_ERROR.getMessage());
    }

}