package choorai.retrospect.user.entity.value;

import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {

    @DisplayName("이름을 입력받아서 Name 객체를 만들 수 있다.")
    @Test
    void successCreateTest() {
        // given
        String inputValue = "정민주";
        // when
        final Name name = new Name(inputValue);
        // then
        assertThat(name.getValue())
            .isEqualTo(inputValue);
    }

    @ParameterizedTest(name = "{0}은 정해진 이름 길이를 벗어나므로 예외가 발생한다.")
    @ValueSource(strings = {"", "이 문장은 이름 길이를 초과하는 예시입니다.이 문장은 이름 길이를 초과하는 예시입니다."})
    void lengthErrorTest(String inputValue) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Name(inputValue))
            .isInstanceOf(UserException.class)
            .hasMessage(UserErrorCode.NAME_LENGTH_ERROR.getMessage());
    }

}
