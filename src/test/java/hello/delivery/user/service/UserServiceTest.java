package hello.delivery.user.service;

import static hello.delivery.user.infrastructure.UserRole.CUSTOMER;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.UserException;
import hello.delivery.common.exception.UserNotFound;
import hello.delivery.mock.FakeUserRepository;
import hello.delivery.user.domain.AddressUpdate;
import hello.delivery.user.domain.Login;
import hello.delivery.user.domain.PasswordUpdate;
import hello.delivery.user.domain.User;
import hello.delivery.user.domain.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;

    private static final String DEFAULT_NAME = "김우섭";
    private static final String DEFAULT_USERNAME = "wss3325";
    private static final String DEFAULT_PASSWORD = "hihihi3454";
    private static final String DEFAULT_ADDRESS = "대구";
    private static final String NEW_ADDRESS = "서울";
    private static final String NEW_PASSWORD = "hihihi9999";
    private static final String INVALID_SHORT_PASSWORD = "9999";

    @BeforeEach
    void setUp() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        userService = new UserService(fakeUserRepository);
    }

    @Test
    @DisplayName("고객으로 회원가입을 할 수 있다.")
    void signupCustomer() {
        // given
        UserCreate userCreate = createUserCreate();

        // when
        User result = userService.signupCustomer(userCreate);

        // then
        assertThat(result.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(result.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(result.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(result.getRole()).isEqualTo(CUSTOMER);
    }

    @Test
    @DisplayName("사장으로 회원가입을 할 수 있다.")
    void signupOwner() {
        // given
        UserCreate userCreate = createUserCreate();

        // when
        User result = userService.signupOwner(userCreate);

        // then
        assertThat(result.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(result.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(result.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(result.getRole()).isEqualTo(OWNER);
    }

    @Test
    @DisplayName("사용자는 아이디와 비밀번호로 로그인을 할 수 있다.")
    void login() {
        // given
        UserCreate userCreate = createUserCreate();
        userService.signupCustomer(userCreate);
        Login loginRequest = createLoginRequest(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        // when
        User result = userService.login(loginRequest);

        // then
        assertThat(result.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @DisplayName("아이디와 비밀번호 전부 틀리면 예외를 던진다.")
    void invalidLogin() {
        // given
        UserCreate userCreate = createUserCreate();
        userService.signupCustomer(userCreate);
        Login loginRequest = createLoginRequest("zzzzzzz", "hihihi1111");

        // expect
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("아이디나 비밀번호가 틀리면 예외를 던진다.")
    void invalidLoginWrong() {
        // given
        UserCreate userCreate = createUserCreate();
        userService.signupCustomer(userCreate);
        Login loginRequest = createLoginRequest(DEFAULT_USERNAME, "hihihi1111");

        // expect
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("사용자는 주소를 변경할 수 있다.")
    void changeAddress() {
        // given
        UserCreate userCreate = createUserCreate();
        User user = userService.signupCustomer(userCreate);
        AddressUpdate addressUpdate = createAddressUpdate(NEW_ADDRESS);

        // when
        User result = userService.changeAddress(user.getId(), addressUpdate);

        // then
        assertThat(result.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(result.getAddress()).isEqualTo(NEW_ADDRESS);
    }

    @Test
    @DisplayName("사용자는 비밀번호를 변경할 수 있다.")
    void changePassword() {
        // given
        UserCreate userCreate = createUserCreate();
        User user = userService.signupCustomer(userCreate);
        PasswordUpdate passwordUpdate = createPasswordUpdate(NEW_PASSWORD);

        // when
        userService.changePassword(user.getId(), passwordUpdate);
        Login loginRequest = createLoginRequest(DEFAULT_USERNAME, NEW_PASSWORD);

        // then
        assertThat(loginRequest.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(loginRequest.getPassword()).isEqualTo(NEW_PASSWORD);
    }

    @Test
    @DisplayName("비밀번호는 8자 이상 20자 이하로 입력해야 한다.")
    void validateLengthChangePassword() {
        // given
        UserCreate userCreate = createUserCreate();
        User user = userService.signupCustomer(userCreate);
        PasswordUpdate passwordUpdate = createPasswordUpdate(INVALID_SHORT_PASSWORD);

        // expect
        assertThatThrownBy(() -> userService.changePassword(user.getId(), passwordUpdate))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("비밀번호는 8자 이상 20자 이하로 입력 가능합니다.");
    }

    @Test
    @DisplayName("비밀번호 변경 시 기존 비밀번호와 같으면 예외가 발생한다.")
    void validateSamePassword() {
        // given
        UserCreate userCreate = createUserCreate();
        User user = userService.signupCustomer(userCreate);
        PasswordUpdate passwordUpdate = createPasswordUpdate(DEFAULT_PASSWORD);

        // expect
        assertThatThrownBy(() -> userService.changePassword(user.getId(), passwordUpdate))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
    }

    private UserCreate createUserCreate() {
        return UserCreate.builder()
                .name(DEFAULT_NAME)
                .username(DEFAULT_USERNAME)
                .password(DEFAULT_PASSWORD)
                .address(DEFAULT_ADDRESS)
                .build();
    }

    private Login createLoginRequest(String username, String password) {
        return Login.builder()
                .username(username)
                .password(password)
                .build();
    }

    private AddressUpdate createAddressUpdate(String address) {
        return AddressUpdate.builder()
                .address(address)
                .build();
    }

    private PasswordUpdate createPasswordUpdate(String password) {
        return PasswordUpdate.builder()
                .password(password)
                .build();
    }
}
