package hello.delivery.owner.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OwnerTest {

    @Test
    void create() throws Exception {
        // given
        OwnerCreate ownerCreate = OwnerCreate.builder()
                .name("우섭이")
                .password(3454)
                .build();

        // when
        Owner owner = Owner.of(ownerCreate);

        // then
        assertThat(owner.getName()).isEqualTo("우섭이");
    }

}