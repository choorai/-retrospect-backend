package choorai.retrospect.retrospect_room.entity.value;

import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomErrorCode;
import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class ShareLink {

    private static final int SHARE_LINK_MAX_LENGTH = 200;

    @Column(name = "share_link")
    private String value;

    public ShareLink(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.length() > SHARE_LINK_MAX_LENGTH) {
            throw new RetrospectRoomException(RetrospectRoomErrorCode.SHARE_LINK_ERROR);
        }
    }
}
