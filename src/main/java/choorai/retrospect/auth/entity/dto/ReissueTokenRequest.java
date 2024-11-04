package choorai.retrospect.auth.entity.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ReissueTokenRequest {

    String refreshToken;

    public ReissueTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
