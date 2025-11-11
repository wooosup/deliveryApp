package hello.delivery.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.LoginException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("사용자는 회원가입을 할 수 있다.")
    void signup() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();

        // when
        User signupUser = User.signup(userCreate);

        // then
        assertThat(signupUser.getName()).isEqualTo("김우섭");
        assertThat(signupUser.getUsername()).isEqualTo("wss3325");
        assertThat(signupUser.getPassword()).isEqualTo("1234");
        assertThat(signupUser.getAddress()).isEqualTo("대구");
    }

    @Test
    @DisplayName("아이디 또는 비밀번호가 일치하지 않으면 예외를 던진다.")
    void checkNicknameAndPassword() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        User signupUser = User.signup(userCreate);

        // expect
        assertThatThrownBy(() -> signupUser.checkNicknameAndPassword("zzz", "1111"))
                .isInstanceOf(LoginException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

}