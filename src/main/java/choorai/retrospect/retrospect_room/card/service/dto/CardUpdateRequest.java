package choorai.retrospect.retrospect_room.card.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardUpdateRequest {

    private final String type;
    private final String content;

}
