package choorai.retrospect.auth.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReissueTokenRequest {

    String refreshToken;

}