package choorai.retrospect.user.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.entity.value.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@MockUser(email = "test@test.com", password = "passsword1", name = "테스터", role = Role.USER)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
