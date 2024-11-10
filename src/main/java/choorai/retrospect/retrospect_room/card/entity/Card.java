package choorai.retrospect.retrospect_room.card.entity;

import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.retrospect_room.card.entity.value.Type;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "retrospect_room_id")
    private RetrospectRoom retrospectRoom;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Card(Long id, Type type, String content, RetrospectRoom retrospectRoom, User user) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.retrospectRoom = retrospectRoom;
        this.user = user;
    }

    public static Card forSave(String type, String content, RetrospectRoom retrospectRoom, User user) {
        validateParameter(type, content, retrospectRoom, user);
        return new Card(null,
                        Type.valueOf(type),
                        content,
                        retrospectRoom,
                        user);
    }

    private static void validateParameter(String type, String content, RetrospectRoom retrospectRoom, User user) {
        validateType(type);
        validateContent(content);
        validateRetrospectRoom(retrospectRoom);
        validateUser(user);
    }

    private static void validateType(String type) throws CardException {
        if (type == null || type.isEmpty()) {
            throw new CardException(CardErrorCode.TYPE_IS_NOT_NULL);
        }
        if (!Type.isValidType(type)) {
            throw new CardException(CardErrorCode.INVALID_TYPE);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new CardException(CardErrorCode.CONTENT_IS_NOT_NULL);
        }
    }

    private static void validateRetrospectRoom(RetrospectRoom retrospectRoom) {
        if (retrospectRoom == null) {
            throw new CardException(CardErrorCode.RETROSPECT_ROOM_IS_NOT_NULL);
        }
    }

    private static void validateUser(User user) {
        if (user == null) {
            throw new CardException(CardErrorCode.USER_IS_NOT_NULL);
        }
    }

    public boolean isInRoom(Long retrospectRoomId) {
        return Objects.equals(retrospectRoom.getId(), retrospectRoomId);
    }
}
