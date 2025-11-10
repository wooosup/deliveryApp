package hello.delivery.owner.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.UserNotFound;
import hello.delivery.mock.FakeOwnerRepository;
import hello.delivery.owner.domain.Owner;
import hello.delivery.owner.domain.OwnerCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OwnerServiceTest {

    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        FakeOwnerRepository fakeOwnerRepository = new FakeOwnerRepository();
        this.ownerService = new OwnerService(fakeOwnerRepository);
    }

    @Test
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
    void notFoundPassword() throws Exception {
        // expect
        assertThatThrownBy(() -> ownerService.findByPassword("없음"))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }


}