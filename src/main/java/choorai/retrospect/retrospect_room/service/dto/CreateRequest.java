package choorai.retrospect.retrospect_room.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequest {

    private String subject;
    private String details;
    private String retrospectType;
    private String timeLimit;
}
