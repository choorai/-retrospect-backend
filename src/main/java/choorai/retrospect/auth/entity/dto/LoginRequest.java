package choorai.retrospect.auth.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    String email;

    String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
