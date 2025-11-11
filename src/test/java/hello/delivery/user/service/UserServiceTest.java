package hello.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.LoginException;
import hello.delivery.common.exception.UserNotFound;
import hello.delivery.mock.FakeUserRepository;
import hello.delivery.user.domain.Login;
import hello.delivery.user.domain.User;
import hello.delivery.user.domain.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;
    private FakeUserRepository fakeUserRepository;

    @BeforeEach
    void setUp() {
        fakeUserRepository = new FakeUserRepository();
        userService = new UserService(fakeUserRepository);
    }

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
        User user = userService.signup(userCreate);

        // then
        assertThat(user.getName()).isEqualTo("김우섭");
        assertThat(user.getUsername()).isEqualTo("wss3325");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getAddress()).isEqualTo("대구");
    }

    @Test
    @DisplayName("사용자는 username과 password로 로그인을 할 수 있다.")
    void login() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login login = Login.builder()
                .username("wss3325")
                .password("1234")
                .build();

        // when
        User loginUser = userService.login(login);

        // then
        assertThat(loginUser.getUsername()).isEqualTo("wss3325");
        assertThat(loginUser.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("아이디와 비밀번호 전부 틀리면 예외를 던진다.")
    void invalidLogin() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login login = Login.builder()
                .username("zzzz")
                .password("11111")
                .build();

        // expect
        assertThatThrownBy(() -> userService.login(login))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("아이디나 비밀번호가 틀리면 예외를 던진다.")
    void invalidLoginWrong() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login login = Login.builder()
                .username("wss3325")
                .password("11111")
                .build();

        // expect
        assertThatThrownBy(() -> userService.login(login))
                .isInstanceOf(LoginException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }


}