package choorai.retrospect.card.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequest {

    private String type;
    private String content;
    private Long retrospectRoomId;

    public CardRequest(String type, String content, Long retrospectRoomId) {
        this.type = type;
        this.content = content;
        this.retrospectRoomId = retrospectRoomId;
    }

    public static CardRequest ofCreate(String type, String content, long retrospectRoomId) {
        return new CardRequest(type, content, retrospectRoomId);
    }

    public static CardRequest ofUpdate(String type, String content) {
        return new CardRequest(type, content, null);
    }
}
