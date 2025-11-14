package hello.delivery.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.OrderException;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("user, store, orderProducts로 주문을 생성할 수 있다.")
    void order() throws Exception {
        // given
        Owner owner = buildOwner();
        Store store = buildStore(owner);
        User user = buildUser();
        Product product = buildProduct(store);
        OrderProduct orderProduct = OrderProduct.create(product, 2);

        // when
        Order order = Order.order(user, store, List.of(orderProduct));

        // then
        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getStore()).isEqualTo(store);
        assertThat(order.getOrderProducts()).hasSize(1);
        assertThat(order.getTotalPrice()).isEqualTo(40000);
    }

    @Test
    @DisplayName("user가 null이면 예외를 던진다.")
    void validateOrderNotUser() throws Exception {
        // given
        Owner owner = buildOwner();
        Store store = buildStore(owner);
        Product product = buildProduct(store);
        OrderProduct orderProduct = OrderProduct.create(product, 2);
        // expect
        assertThatThrownBy(() -> Order.order(null, store, List.of(orderProduct)))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("주문하는 사용자는 필수입니다.");
    }

    @Test
    @DisplayName("store가 null이면 예외를 던진다.")
    void validateOrderNotStore() throws Exception {
        // given
        Owner owner = buildOwner();
        Store store = buildStore(owner);
        Product product = buildProduct(store);
        User user = buildUser();
        OrderProduct orderProduct = OrderProduct.create(product, 2);

        // expect
        assertThatThrownBy(() -> Order.order(user, null, List.of(orderProduct)))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("주문하는 가게는 필수입니다.");
    }

    @Test
    @DisplayName("orderProducts가 null이면 예외를 던진다.")
    void validateOrderNotOrderProducts() throws Exception {
        // given
        Owner owner = buildOwner();
        Store store = buildStore(owner);
        User user = buildUser();

        // expect
        assertThatThrownBy(() -> Order.order(user, store, null))
                .isInstanceOf(OrderException.class)
                .hasMessageContaining("주문에는 최소 1개 이상의 상품이 포함되어야 합니다.");
    }


    private static Owner buildOwner() {
        return Owner.builder()
                .id(1L)
                .name("우섭이")
                .password("3454")
                .build();
    }

    private static Store buildStore(Owner owner) {
        return Store.builder()
                .id(1L)
                .name("BBQ")
                .owner(owner)
                .build();
    }

    private static User buildUser() {
        return User.builder()
                .id(1L)
                .name("김우섭")
                .username("wss3325")
                .password("3454")
                .address("대구")
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