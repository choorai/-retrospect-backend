package choorai.retrospect.retrospect_room.card.entity.value;

import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Content {

    @Column(name = "content")
    private String value;

    public Content(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String content) {
        if (content == null || content.isEmpty()) {
            throw new CardException(CardErrorCode.CONTENT_IS_NOT_NULL);
        }
    }

}
