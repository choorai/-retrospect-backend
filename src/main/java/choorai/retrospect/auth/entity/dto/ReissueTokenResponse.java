package choorai.retrospect.auth.entity.dto;

import lombok.Getter;

@Getter
public class ReissueTokenResponse {

    private String accessToken;

    public ReissueTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
