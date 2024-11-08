package choorai.retrospect.card.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequest {

    private String type;
    private String content;

    public CardRequest(String type, String content) {
        this.type = type;
        this.content = content;
    }
}
