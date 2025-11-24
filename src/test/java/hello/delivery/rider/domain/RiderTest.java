package hello.delivery.rider.domain;

import static hello.delivery.rider.domain.RiderStatus.AVAILABLE;
import static hello.delivery.rider.domain.RiderStatus.DELIVERING;
import static hello.delivery.rider.domain.RiderStatus.OFFLINE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RiderTest {

    @Test
    @DisplayName("라이더 회원가입")
    void signup() throws Exception {
        // given
        RiderCreate riderCreate = RiderCreate.builder()
                .name("없을무")
                .phone("010-1234-5678")
                .build();

        // when
        Rider result = Rider.signup(riderCreate);

        // then
        assertThat(result.getName()).isEqualTo("없을무");
        assertThat(result.getPhone()).isEqualTo("010-1234-5678");
        assertThat(result.getStatus()).isEqualTo(OFFLINE);
    }

    @Test
    @DisplayName("라이더 로그인")
    void login() throws Exception {
        // given
        RiderCreate riderCreate = RiderCreate.builder()
                .name("없을무")
                .phone("010-1234-5678")
                .build();
        RiderLogin riderLogin = RiderLogin.builder()
                .phone("010-1234-5678")
                .build();

        // when
        Rider result = Rider.signup(riderCreate).login(riderLogin);

        // then
        assertThat(result.getName()).isEqualTo("없을무");
        assertThat(result.getPhone()).isEqualTo("010-1234-5678");
        assertThat(result.getStatus()).isEqualTo(AVAILABLE);
    }

    @Test
    @DisplayName("라이더 상태 변경")
    void changeStatus() throws Exception {
        // given
        RiderCreate riderCreate = RiderCreate.builder()
                .name("없을무")
                .phone("010-1234-5678")
                .build();
        RiderStatusUpdate statusUpdate = RiderStatusUpdate.builder()
                .status(DELIVERING)
                .build();

        // when
        Rider result = Rider.signup(riderCreate).changeStatus(statusUpdate);

        // then
        assertThat(result.getName()).isEqualTo("없을무");
        assertThat(result.getPhone()).isEqualTo("010-1234-5678");
        assertThat(result.getStatus()).isEqualTo(DELIVERING);
    }

}