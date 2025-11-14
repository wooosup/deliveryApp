package hello.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.LoginException;
import hello.delivery.common.exception.UserNotFound;
import hello.delivery.mock.FakeUserRepository;
import hello.delivery.user.domain.User;
import hello.delivery.user.domain.Login;
import hello.delivery.user.domain.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
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
        User result = userService.signup(userCreate);

        // then
        assertThat(result.getName()).isEqualTo("김우섭");
        assertThat(result.getUsername()).isEqualTo("wss3325");
        assertThat(result.getAddress()).isEqualTo("대구");
    }

    @Test
    @DisplayName("사용자는 아이디와 비밀번호로 로그인을 할 수 있다.")
    void login() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login user = Login.builder()
                .username("wss3325")
                .password("1234")
                .build();

        // when
        User result = userService.login(user);

        // then
        assertThat(result.getUsername()).isEqualTo("wss3325");
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
        Login user = Login.builder()
                .username("zzzz")
                .password("11111")
                .build();

        // expect
        assertThatThrownBy(() -> userService.login(user))
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
        Login user = Login.builder()
                .username("wss3325")
                .password("11111")
                .build();

        // expect
        assertThatThrownBy(() -> userService.login(user))
                .isInstanceOf(LoginException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }


    @Test
    @DisplayName("사용자는 주소를 변경할 수 있다.")
    void changeAddress() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        User user = userService.signup(userCreate);

        // when
        User result = userService.changeAddress(user.getId(), "서울");

        // then
        assertThat(result.getUsername()).isEqualTo("wss3325");
        assertThat(result.getAddress()).isEqualTo("서울");
    }


    @Test
    @DisplayName("사용자는 비밀번호를 변경할 수 있다.")
    void changePassword() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        User user = userService.signup(userCreate);

        // when
        userService.changePassword(user.getId(), "9999");
        Login result = Login.builder()
                .username("wss3325")
                .password("9999")
                .build();

        // then
        assertThat(result.getUsername()).isEqualTo("wss3325");
        assertThat(result.getPassword()).isEqualTo("9999");
    }

}