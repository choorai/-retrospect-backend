package choorai.retrospect.retrospect_room;

import choorai.retrospect.retrospect_room.entity.value.Subject;
import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static choorai.retrospect.retrospect_room.excpetion.RetrospectRoomErrorCode.SUBJECT_LENGTH_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubjectTest {

    @DisplayName("회고 주제 글자 수가 100자 넘으면 예외를 발생한다.")
    @Test
    void return_error_with_wrong_value() {
        // given
        String value = "글".repeat(101);
        // when
        // then
        assertThatThrownBy(() -> new Subject(value))
            .isInstanceOf(RetrospectRoomException.class)
            .hasMessage(SUBJECT_LENGTH_ERROR.getMessage());
    }
}
