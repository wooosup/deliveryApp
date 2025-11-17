package hello.delivery.order.service;

import static hello.delivery.user.infrastructure.UserRole.CUSTOMER;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;

import hello.delivery.mock.FakeFinder;
import hello.delivery.mock.FakeOrderRepository;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderCreate;
import hello.delivery.order.domain.OrderProductRequest;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    private OrderService orderService;
    private FakeFinder fakeFinder;

    @BeforeEach
    void setUp() {
        FakeOrderRepository fakeOrderRepository = new FakeOrderRepository();
        fakeFinder = new FakeFinder();
        TestClockHolder testClockHolder = new TestClockHolder();
        orderService = new OrderService(fakeOrderRepository, fakeFinder, testClockHolder);
    }

    private OrderCreate setUpOrderCreate() {
        User owner = buildOwner();
        User user = buildUser();
        Store store = buildStore(owner);
        Product product = buildProduct(store);
        OrderProductRequest orderProduct = OrderProductRequest.builder()
                .productId(product.getId())
                .quantity(2)
                .build();
        return OrderCreate.builder()
                .userId(user.getId())
                .storeId(store.getId())
                .orderProducts(List.of(orderProduct))
                .build();
    }

    @Test
    @DisplayName("주문을 생성할 수 있다.")
    void order() throws Exception {
        // given
        OrderCreate orderCreate = setUpOrderCreate();

        // when
        Order order = orderService.order(orderCreate);

        // then
        assertThat(order.getOrderProducts()).hasSize(1);
        assertThat(order.getOrderProducts().get(0).getProduct().getName()).isEqualTo("치킨");
        assertThat(order.getOrderProducts().get(0).getQuantity()).isEqualTo(2);
        assertThat(order.getTotalPrice()).isEqualTo(40000);
    }

    @Test
    @DisplayName("사용자 ID로 주문 내역을 조회할 수 있다.")
    void findOrdersByUser() throws Exception {
        // given
        OrderCreate orderCreate = setUpOrderCreate();
        orderService.order(orderCreate);

        // when
        List<Order> result = orderService.findOrdersByUserId(orderCreate.getUserId());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getName()).isEqualTo("김우섭");
        assertThat(result.get(0).getStore().getName()).isEqualTo("BBQ");
        assertThat(result.get(0).getTotalPrice()).isEqualTo(40000);
    }

    private User buildOwner() {
        User owner = User.builder()
                .id(1L)
                .name("차상훈")
                .username("wss3325")
                .password("hihihi3454")
                .address("대구")
                .role(OWNER)
                .build();
        fakeFinder.addOwner(owner);
        return owner;
    }

    private User buildUser() {
        User user = User.builder()
                .id(2L)
                .name("김우섭")
                .username("wss3454")
                .password("hihihi3454")
                .address("대구")
                .role(CUSTOMER)
                .build();
        fakeFinder.addUser(user);
        return user;
    }

    private Store buildStore(User owner) {
        Store store = Store.builder()
                .id(1L)
                .name("BBQ")
                .owner(owner)
                .build();
        fakeFinder.addStore(store);
        return store;
    }

    private Product buildProduct(Store store) {
        Product product = Product.builder()
                .id(1L)
                .name("치킨")
                .price(20000)
                .store(store)
                .build();
        fakeFinder.addProduct(product);
        return product;
    }

}