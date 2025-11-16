package hello.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.UserException;
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
                .password("hihihi3454")
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
                .password("hihihi3454")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login user = Login.builder()
                .username("wss3325")
                .password("hihihi3454")
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
                .password("hihihi3454")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login user = Login.builder()
                .username("zzzzzzz")
                .password("hihihi1111")
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
                .password("hihihi3454")
                .address("대구")
                .build();
        userService.signup(userCreate);
        Login user = Login.builder()
                .username("wss3325")
                .password("hihihi1111")
                .build();

        // expect
        assertThatThrownBy(() -> userService.login(user))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }


    @Test
    @DisplayName("사용자는 주소를 변경할 수 있다.")
    void changeAddress() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("hihihi3454")
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
                .password("hihihi3454")
                .address("대구")
                .build();
        User user = userService.signup(userCreate);

        // when
        userService.changePassword(user.getId(), "hihihi9999");
        Login result = Login.builder()
                .username("wss3325")
                .password("hihihi9999")
                .build();

        // then
        assertThat(result.getUsername()).isEqualTo("wss3325");
        assertThat(result.getPassword()).isEqualTo("hihihi9999");
    }

    @Test
    @DisplayName("비밀변호는 8자 이상 20자 이하로 입력해야 한다.")
    void validateLengthChangePassword() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("hihihi3454")
                .address("대구")
                .build();
        User user = userService.signup(userCreate);

        // expect
        assertThatThrownBy(() -> userService.changePassword(user.getId(), "1234567"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("비밀번호는 8자 이상 20자 이하로 입력 가능합니다.");
    }

    @Test
    @DisplayName("비밀변호 변경 시 기존 비밀번호와 같으면 예외가 발생한다.")
    void validateSamePassword() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .name("김우섭")
                .username("wss3325")
                .password("hihihi3454")
                .address("대구")
                .build();
        User user = userService.signup(userCreate);

        // expect
        assertThatThrownBy(() -> userService.changePassword(user.getId(), "hihihi3454"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
    }

}