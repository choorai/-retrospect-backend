package choorai.retrospect.global.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonExceptionTest {

    @DisplayName("Custom Exception의 정보를 JSON 형태로 반환한다.")
    @Test
    void getInputValueTest() {
        // given
        final CommonException commonException = new CommonException(TestError.TEST_ERROR_CODE,
                                                                    Map.of("정보1", "내용1", "정보2", "내용2"));
        // when
        final String inputValue = commonException.getInputValue();

        // then
        final String prefix = inputValue.substring(0, 1);
        final String rawValue = inputValue.substring(1, inputValue.length() - 1);
        final List<String> values = Arrays.asList(rawValue.split(", "));
        final String suffix = inputValue.substring(inputValue.length() - 1);
        assertAll(
            () -> assertEquals("{", prefix),
            () -> assertEquals("}", suffix),
            () -> assertEquals(2, values.size()),
            () -> assertTrue(values.contains("정보1 : 내용1")),
            () -> assertTrue(values.contains("정보2 : 내용2"))
        );
    }

}
