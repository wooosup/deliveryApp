package hello.delivery.owner.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OwnerTest {

    @Test
    @DisplayName("사용자 생성이 가능하다.")
    void create() throws Exception {
        // given
        OwnerCreate ownerCreate = OwnerCreate.builder()
                .name("우섭이")
                .password("3454")
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
                .id(1L)
                .name("우섭이")
                .password("3454")
                .build();

        // when
        Owner changedOwner = owner.changePassword("9999");

        // then
        assertThat(changedOwner.getPassword()).isEqualTo("9999");
    }

}