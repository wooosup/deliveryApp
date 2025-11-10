package hello.delivery.owner.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.UserNotFound;
import hello.delivery.mock.FakeOwnerRepository;
import hello.delivery.owner.domain.Owner;
import hello.delivery.owner.domain.OwnerCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OwnerServiceTest {

    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        FakeOwnerRepository fakeOwnerRepository = new FakeOwnerRepository();
        this.ownerService = new OwnerService(fakeOwnerRepository);
    }

    @Test
    @DisplayName("OwnerCreate로 회원가입을 한다.")
    void signup() throws Exception {
        // given
        OwnerCreate owner = OwnerCreate.builder()
                .name("우섭이")
                .password(3454)
                .build();

        // when
        Owner result = ownerService.signup(owner);

        // then
        assertThat(result.getName()).isEqualTo("우섭이");
        assertThat(result.getPassword()).isEqualTo(3454);
    }

    @Test
    @DisplayName("이름으로 비밀번호를 찾는다.")
    void findByPassword() throws Exception {
        // given
        OwnerCreate owner = OwnerCreate.builder()
                .name("우섭이")
                .password(3454)
                .build();

        ownerService.signup(owner);

        // when
        String password = ownerService.findByPassword(owner.getName());

        // then
        assertThat(password).isEqualTo("3454");
    }

    @Test
    @DisplayName("존재하지 않는 이름으로 비밀번호를 찾으면 예외가 발생한다.")
    void notFoundPassword() throws Exception {
        // expect
        assertThatThrownBy(() -> ownerService.findByPassword("없음"))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }


}