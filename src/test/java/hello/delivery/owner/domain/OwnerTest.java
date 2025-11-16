package hello.delivery.owner.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.OwnerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OwnerTest {

    @Test
    @DisplayName("사용자 생성이 가능하다.")
    void create() throws Exception {
        // given
        OwnerCreate ownerCreate = OwnerCreate.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();

        // when
        Owner owner = Owner.of(ownerCreate);

        // then
        assertThat(owner.getName()).isEqualTo("우섭이");
    }

    @Test
    @DisplayName("비밀번호를 변경할 수 있다.")
    void changePassword() throws Exception {
        // given
        Owner owner = Owner.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();

        // when
        Owner changedOwner = owner.changePassword("hihihi9999");

        // then
        assertThat(changedOwner.getPassword()).isEqualTo("hihihi9999");
    }

    @Test
    @DisplayName("비밀번호는 8자 이상 20자 이하로 입력해야 한다.")
    void validateLengthPassword() throws Exception {
        // given
        Owner owner = Owner.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();

        // expect
        assertThatThrownBy(() -> owner.changePassword("1234567"))
                .isInstanceOf(OwnerException.class)
                .hasMessageContaining("비밀번호는 8자 이상 20자 이하로 입력 가능합니다.");
    }


    @Test
    @DisplayName("비밀변호 변경 시 기존 비밀번호와 같으면 예외가 발생한다.")
    void validateChangePassword() throws Exception {
        // given
        Owner owner = Owner.builder()
                .name("우섭이")
                .password("hihihi3454")
                .build();

        // expect
        assertThatThrownBy(() -> owner.changePassword("hihihi3454"))
                .isInstanceOf(OwnerException.class)
                .hasMessageContaining("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
    }

}