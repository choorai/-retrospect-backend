package choorai.retrospect.user.entity;

import choorai.retrospect.user.entity.value.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserTest {

    @DisplayName("이메일, 이름, 비밀번호를 활용한 User 생성 테스트")
    @Test
    void createUserTest() {
        // given
        final String emailInput = "alswn@gmail.com";
        final String passwordInput = "adjflajflk12391203";
        final String nameInput = "정민주";
        // when
        final User user = new User(emailInput, passwordInput, nameInput);
        // then
        assertAll(
            () -> assertThat(user.getEmail().getValue())
                .isEqualTo(emailInput),
            () -> assertThat(user.getName().getValue())
                .isEqualTo(nameInput),
            () -> assertThat(user.getPassword())
                .isEqualTo(passwordInput)
        );
    }

    @DisplayName("이메일, 이름, 비밀번호, 회사, 부서, 직급을 활용한 User 생성 테스트")
    @Test
    void createUserTest2() {
        // given
        final String emailInput = "alswn@gmail.com";
        final String passwordInput = "adjflajflk12391203";
        final String nameInput = "정민주";
        final String companyInput = "회사이름";
        final String departmentInput = "부서이름";
        final String positionInput = "직급이름";
        // when
        final User user = new User(emailInput, passwordInput, nameInput, companyInput, departmentInput, positionInput,
                                   Role.USER);
        // then
        assertAll(
            () -> assertThat(user.getEmail().getValue())
                .isEqualTo(emailInput),
            () -> assertThat(user.getName().getValue())
                .isEqualTo(nameInput),
            () -> assertThat(user.getPassword())
                .isEqualTo(passwordInput),
            () -> assertThat(user.getCompanyName())
                .isEqualTo(companyInput),
            () -> assertThat(user.getDepartment())
                .isEqualTo(departmentInput),
            () -> assertThat(user.getPosition())
                .isEqualTo(positionInput)
        );
    }
}
