package choorai.retrospect.retrospect_room.card.entity;

import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static choorai.retrospect.retrospect_room.card.exception.CardErrorCode.CONTENT_IS_NOT_NULL;
import static choorai.retrospect.retrospect_room.card.exception.CardErrorCode.INVALID_TYPE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    private static final String type = "KEEP";
    private static final String content = "내용";
    private static final RetrospectRoom retrospectRoom = RetrospectRoom.forSave("회고주제", "회고 상새 내용", "KPT", "01:00:00",
                                                                                "shareLink");
    private static final User user = new User("alafl@test.com", "afpafdjp132", "테스터");

    @DisplayName("Card의 content가 비어있음 예외가 발생한다.")
    @Test
    void nullContentTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Card.forSave(type, null, retrospectRoom, user))
            .isInstanceOf(CardException.class)
            .hasMessage(CONTENT_IS_NOT_NULL.getMessage());
    }

    @DisplayName("Card의 Type이 유효한 type(KEEP, PROBLEM, TRY)이 아니면 예외가 발생한다.")
    @Test
    void invalidTypeTest() {
        // given
        String invalidType = "k";
        // when
        // then
        assertThatThrownBy(() -> Card.forSave(invalidType, content, retrospectRoom, user))
            .isInstanceOf(CardException.class)
            .hasMessage(INVALID_TYPE.getMessage());
    }

}
