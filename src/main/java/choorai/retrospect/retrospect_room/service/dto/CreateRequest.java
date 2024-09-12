package choorai.retrospect.retrospect_room.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRequest {

    private String subject;
    private String details;
    private String retrospectType;
    private String timeLimit;
}
