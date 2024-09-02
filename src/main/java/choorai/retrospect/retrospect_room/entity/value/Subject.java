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
public class Subject {

    private static final int SUBJECT_MAX_LENGTH = 100;

    @Column(name = "subject")
    private String value;

    public Subject(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.length() > SUBJECT_MAX_LENGTH) {
            throw new RetrospectRoomException(RetrospectRoomErrorCode.SUBJECT_LENGTH_ERROR);
        }
    }
}
