package choorai.retrospect.card.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequest {

    private String type;
    private String content;
    private long retrospectRoomId;

    public CardRequest(String type, String content, long retrospectRoomId) {
        this.type = type;
        this.content = content;
        this.retrospectRoomId = retrospectRoomId;
    }
}
