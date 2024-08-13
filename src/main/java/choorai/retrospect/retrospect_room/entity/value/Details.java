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
public class Details {

    private static final int DETAILS_MAX_LENGTH = 500;

    @Column(name = "details", columnDefinition = "TEXT")
    private String value;

    public Details(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) throws RetrospectRoomException {
        if (value.length() > DETAILS_MAX_LENGTH) {
            throw new RetrospectRoomException(RetrospectRoomErrorCode.DETAILS_LENGTH_ERROR);
        }
    }
}
