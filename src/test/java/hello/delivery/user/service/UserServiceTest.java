package hello.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("")
    void login() throws Exception {
        // given
        User user = User.builder()
                .name("김우섭")
                .username("wss3325")
                .password("1234")
                .address("대구")
                .build();
        fakeUserRepository.save(user);
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

}