package choorai.retrospect.retrospect_room.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateResponse {

    @JsonProperty("share_link")
    private String shareLink;

}
