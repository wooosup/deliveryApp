package hello.delivery.order.domain;

import static hello.delivery.user.infrastructure.UserRole.CUSTOMER;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.OrderException;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("주문을 생성할 수 있다.")
    void order() throws Exception {
        // given
        User owner = buildOwner();
        User user = buildUser();
        Store store = buildStore(owner);
        Product product = buildProduct(store);
        OrderProduct orderProduct = OrderProduct.create(product, 2);

        // when
        Order order = Order.order(user, store, List.of(orderProduct), new TestClockHolder());

        // then
        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getStore()).isEqualTo(store);
        assertThat(order.getOrderProducts()).hasSize(1);
        assertThat(order.getTotalPrice()).isEqualTo(40000);
    }

    @Test
    @DisplayName("사용자가 null이면 예외를 던진다.")
    void validateOrderNotUser() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        Product product = buildProduct(store);
        OrderProduct orderProduct = OrderProduct.create(product, 2);
        // expect
        assertThatThrownBy(() -> Order.order(null, store, List.of(orderProduct), new TestClockHolder()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("주문하는 사용자는 필수입니다.");
    }

    @Test
    @DisplayName("가게가 null이면 예외를 던진다.")
    void validateOrderNotStore() throws Exception {
        // given
        User owner = buildOwner();
        User user = buildUser();
        Store store = buildStore(owner);
        Product product = buildProduct(store);
        OrderProduct orderProduct = OrderProduct.create(product, 2);

        // expect
        assertThatThrownBy(() -> Order.order(user, null, List.of(orderProduct), new TestClockHolder()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("주문하는 가게는 필수입니다.");
    }

    @Test
    @DisplayName("삼품들이 null이면 예외를 던진다.")
    void validateOrderNotOrderProducts() throws Exception {
        // given
        User owner = buildOwner();
        User user = buildUser();
        Store store = buildStore(owner);

        // expect
        assertThatThrownBy(() -> Order.order(user, store, null, new TestClockHolder()))
                .isInstanceOf(OrderException.class)
                .hasMessageContaining("주문에는 최소 1개 이상의 상품이 포함되어야 합니다.");
    }

    private static User buildOwner() {
        return User.builder()
                .id(1L)
                .name("차상훈")
                .username("wss3325")
                .password("hihihi3454")
                .address("대구")
                .role(OWNER)
                .build();
    }

    private static User buildUser() {
        return User.builder()
                .id(2L)
                .name("김우섭")
                .username("wss3325")
                .password("3454")
                .address("대구")
                .role(CUSTOMER)
                .build();
    }

    private static Store buildStore(User owner) {
        return Store.builder()
                .id(1L)
                .name("BBQ")
                .owner(owner)
                .build();
    }

    private static Product buildProduct(Store store) {
        return Product.builder()
                .name("치킨")
                .price(20000)
                .store(store)
                .build();
    }

}