package choorai.retrospect.retrospect_room.card.entity;

import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.retrospect_room.card.entity.value.Content;
import choorai.retrospect.retrospect_room.card.entity.value.Type;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.user.entity.User;
import jakarta.persistence.Embedded;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Embedded
    private Content content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "retrospect_room_id")
    private RetrospectRoom retrospectRoom;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Card(final Long id, final Type type, final Content content, final RetrospectRoom retrospectRoom,
                 final User user) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.retrospectRoom = retrospectRoom;
        this.user = user;
        retrospectRoom.addCard(this);
        user.addCard(this);
    }

    public static Card forSave(final String type, final String content, final RetrospectRoom retrospectRoom,
                               final User user) {
        return new Card(null,
                        Type.fromString(type),
                        new Content(content),
                        retrospectRoom,
                        user);
    }

    public String getContent() {
        return this.content.getValue();
    }

    public void update(final Long retrospectRoomId, final String typeValue, final String content) {
        validateCardInRoom(retrospectRoomId);
        if (typeValue != null) {
            this.type = Type.fromString(typeValue);
        }
        if (content != null) {
            this.content = new Content(content);
        }
    }

    private void validateCardInRoom(final Long retrospectRoomId) {
        if (!Objects.equals(retrospectRoom.getId(), retrospectRoomId)) {
            throw new CardException(CardErrorCode.CARD_IS_NOT_IN_ROOM);
        }
    }
}
