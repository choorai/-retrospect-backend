package choorai.retrospect.retrospect_room;

import choorai.retrospect.retrospect_room.entity.value.ShareLink;
import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static choorai.retrospect.retrospect_room.excpetion.RetrospectRoomErrorCode.SHARE_LINK_ERROR;

class ShareLinkTest {

    @DisplayName("공유링크의 길이가 200자를 넘기면 예외를 발생한다.")
    @Test
    void return_error_with_wrong_value() {
        // given
        String value = "글".repeat(201);
        // when
        // then
        Assertions.assertThatThrownBy(() -> new ShareLink(value))
            .isInstanceOf(RetrospectRoomException.class)
            .hasMessage(SHARE_LINK_ERROR.getMessage());
    }
}
