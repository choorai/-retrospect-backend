package choorai.retrospect.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonExceptionTest {

    @DisplayName("Custom Exception의 정보를 Key, Value 형태로 반환한다. (구분자 = // )")
    @Test
    void getInputValueTest() {
        // given
        final CommonException commonException = new CommonException(TestError.TEST_ERROR_CODE,
                                                                    Map.of("정보1", "내용1", "정보2", "내용2"));
        // when
        final String inputValue = commonException.getInputValue();
        // then
        final String[] inputValues = inputValue.split(" // ");
        assertThat(inputValues)
            .hasSize(2)
            .containsExactly("Key: 정보1, Value: 내용1", "Key: 정보2, Value: 내용2");
    }

}
