package choorai.retrospect.retrospect_room;

import choorai.retrospect.retrospect_room.entity.value.Details;
import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static choorai.retrospect.retrospect_room.excpetion.RetrospectRoomErrorCode.DETAILS_LENGTH_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DetailsTest {

    @DisplayName("회고 상세 내용 글자 수가 500자를 넘기면 예외를 발생한다.")
    @Test
    void return_error_with_wrong_value() {
        // given
        String value = "글".repeat(501);
        // when
        // then
        assertThatThrownBy(() -> new Details(value))
            .isInstanceOf(RetrospectRoomException.class)
            .hasMessage(DETAILS_LENGTH_ERROR.getMessage());
    }
}
