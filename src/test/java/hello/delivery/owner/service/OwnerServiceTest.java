package hello.delivery.owner.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.OwnerException;
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
    @DisplayName("회원가입을 할 수 있다.")
    void signup() throws Exception {
        // given
        OwnerCreate owner = OwnerCreate.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();

        // when
        Owner result = ownerService.signup(owner);

        // then
        assertThat(result.getName()).isEqualTo("우섭이");
        assertThat(result.getPassword()).isEqualTo("hihihi3454");
    }

    @Test
    @DisplayName("비밀번호를 변경할 수 있다.")
    void changePassword() throws Exception {
        // given
        OwnerCreate ownerCreate = OwnerCreate.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();
        Owner owner = ownerService.signup(ownerCreate);

        // when
        Owner result = ownerService.changePassword(owner.getId(), "hihihi9999");

        // then
        assertThat(result.getPassword()).isEqualTo("hihihi9999");
    }

    @Test
    @DisplayName("비밀변호 변경 시 사용자를 찾지 못하면 예외가 발생한다.")
    void notFoundOwner() throws Exception {
        // expect
        assertThatThrownBy(() -> ownerService.changePassword(1L, "hihihi9999"))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("비밀변호 변경 시 기존 비밀번호와 같으면 예외가 발생한다.")
    void validateSamePassword() throws Exception {
        // given
        OwnerCreate ownerCreate = OwnerCreate.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();
        Owner owner = ownerService.signup(ownerCreate);

        // expect
        assertThatThrownBy(() -> ownerService.changePassword(owner.getId(), "hihihi3454"))
                .isInstanceOf(OwnerException.class)
                .hasMessageContaining("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
    }


}