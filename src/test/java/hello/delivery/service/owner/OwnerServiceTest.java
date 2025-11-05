package hello.delivery.service.owner;

import static org.assertj.core.api.Assertions.assertThat;

import hello.delivery.service.owner.request.OwnerCreate;
import hello.delivery.service.owner.response.OwnerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    void create() throws Exception {
        // given
        OwnerCreate owner = OwnerCreate.builder()
                .name("우섭이")
                .password(3454)
                .build();

        // when
        OwnerResponse response = ownerService.create(owner);

        // then
        assertThat(response.getName()).isEqualTo("우섭이");
        assertThat(response.getPassword()).isEqualTo(3454);
    }

}