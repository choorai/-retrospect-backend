package choorai.retrospect.user.service;


import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.value.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MockUser(email = "test@test.com", password = "passsword1", name = "테스터", role = Role.USER)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;


    @DisplayName("유저 정보 조회")
    @Test
    void getCurrentUserSuccessTest() {
        // given
        // when
        User result = userService.getCurrentUser();

        // Assert
        assertAll(
            () -> assertNotNull(result),
            () -> assertThat(result.getEmail().getValue()).isEqualTo("test@test.com"),
            () -> assertThat(result.getPassword()).isEqualTo("passsword1"),
            () -> assertThat(result.getName().getValue()).isEqualTo("테스터"),
            () -> assertThat(result.getRole()).isEqualTo(Role.USER)
        );
    }

}
