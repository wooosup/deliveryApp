package hello.delivery.delivery.domain;

import static hello.delivery.delivery.domain.DeliveryStatus.ASSIGNED;
import static hello.delivery.delivery.domain.DeliveryStatus.DELIVERED;
import static hello.delivery.delivery.domain.DeliveryStatus.PENDING;
import static hello.delivery.delivery.domain.DeliveryStatus.PICKED_UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.DeliveryException;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderProduct;
import hello.delivery.rider.domain.Rider;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryTest {

    @Test
    @DisplayName("배달을 생성 할 수 있다.")
    void create() throws Exception {
        // given
        DeliveryAddress address = createAddress();
        Order order = buildOrder(address);

        // when
        Delivery delivery = Delivery.create(order);

        // then
        assertThat(delivery.getOrderId()).isEqualTo(order.getId());
        assertThat(delivery.getAddress()).isEqualTo(order.getAddress());
        assertThat(delivery.getStatus()).isEqualTo(PENDING);
    }

    @Test
    @DisplayName("주소 없이 배달을 생성하면 예외를 던진다.")
    void validateCreate() throws Exception {
        // given
        Order order = buildOrder(null);

        assertThatThrownBy(() -> Delivery.create(order))
                .isInstanceOf(DeliveryException.class)
                .hasMessageContaining("배달에 필요한 주소 정보가 없습니다.");
    }

    @Test
    @DisplayName("ASSIGNED 상태에서 배달을 시작하면 PICKED_UP 상태가 되고 시작 시간이 기록된다.")
    void start() throws Exception {
        // given
        TestClockHolder testClockHolder = new TestClockHolder();
        Order order = buildOrder(createAddress());
        Rider rider = buildRider();
        Delivery delivery = Delivery.builder()
                .address(createAddress())
                .orderId(order.getId())
                .riderId(rider.getId())
                .status(ASSIGNED)
                .startedAt(null)
                .build();

        // when
        Delivery result = delivery.start(rider.getId(), testClockHolder);

        // then
        assertThat(result.getStatus()).isEqualTo(PICKED_UP);
        assertThat(result.getStartedAt()).isEqualTo(testClockHolder.nowDateTime());
        assertThat(result).isNotSameAs(delivery);
    }

    @Test
    @DisplayName("ASSIGNED 상태가 아니면 배달을 시작할 수 없다.")
    void validateStart() {
        // given
        TestClockHolder testClockHolder = new TestClockHolder();
        Rider rider = buildRider();
        Delivery delivery = Delivery.builder()
                .address(createAddress())
                .orderId(buildOrder(createAddress()).getId())
                .riderId(rider.getId())
                .status(PENDING)
                .build();

        // when & then
        assertThatThrownBy(() -> delivery.start(rider.getId(), testClockHolder))
                .isInstanceOf(DeliveryException.class)
                .hasMessage("배달을 시작할 수 없는 상태입니다.");
    }

    @Test
    @DisplayName("PICKED_UP 상태에서 배달을 완료하면 DELIVERED 상태가 되고 완료 시간이 기록된다.")
    void complete() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2025, 11, 23, 14, 30);
        TestClockHolder testClockHolder = new TestClockHolder();
        Rider rider = buildRider();

        Delivery delivery = Delivery.builder()
                .address(createAddress())
                .orderId(buildOrder(createAddress()).getId())
                .riderId(rider.getId())
                .status(PICKED_UP)
                .startedAt(startTime)
                .build();

        // when
        Delivery completedDelivery = delivery.complete(rider.getId(), testClockHolder);

        // then
        assertThat(completedDelivery.getStatus()).isEqualTo(DELIVERED);
        assertThat(completedDelivery.getStartedAt()).isEqualTo(startTime);
        assertThat(completedDelivery.getCompletedAt()).isEqualTo(testClockHolder.nowDateTime());
    }

    @Test
    @DisplayName("PICKED_UP 상태가 아니면 배달을 완료할 수 없다.")
    void validateComplete() {
        // given
        TestClockHolder testClockHolder = new TestClockHolder();
        Rider rider = buildRider();
        Delivery delivery = Delivery.builder()
                .address(createAddress())
                .orderId(buildOrder(createAddress()).getId())
                .riderId(rider.getId())
                .status(ASSIGNED)
                .build();

        // when & then
        assertThatThrownBy(() -> delivery.complete(rider.getId(), testClockHolder))
                .isInstanceOf(DeliveryException.class)
                .hasMessage("배달을 완료할 수 없는 상태입니다.");
    }

    private DeliveryAddress createAddress() {
        return DeliveryAddress.of("서울시 강남구");
    }

    private OrderProduct buildOrderProduct() {
        return OrderProduct.builder()
                .id(1L)
                .build();
    }

    private Order buildOrder(DeliveryAddress address) {
        return Order.builder()
                .id(1L)
                .address(address)
                .orderProducts(List.of(buildOrderProduct()))
                .build();
    }

    private Rider buildRider() {
        return Rider.builder()
                .id(1L)
                .name("없을무")
                .phone("010-1234-5678")
                .build();
    }

}