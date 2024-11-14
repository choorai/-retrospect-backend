package choorai.retrospect.retrospect_room.card.entity.value;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TypeTest {

    @DisplayName("유효한 type인지 검증할 수 있다.")
    @CsvSource({"keep, KEEP", "PROBLEM, PROBLEM", "tRy, TRY"})
    @ParameterizedTest(name = "{0}의 Type 유효성 검증 결과는 {1}이다.")
    void invalidTest(String typeValue, Type expectedType) {
        // given
        // when
        final Type result = Type.fromString(typeValue);
        // then
        assertThat(result).isEqualTo(expectedType);
    }
}
