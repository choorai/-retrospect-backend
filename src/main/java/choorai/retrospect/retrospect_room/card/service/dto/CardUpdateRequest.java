package choorai.retrospect.retrospect_room.card.service.dto;

import lombok.Getter;

@Getter
public class CardUpdateRequest {

    private String type;
    private String content;

    public CardUpdateRequest(final String type, final String content) {
        this.type = type;
        this.content = content;
    }

}
