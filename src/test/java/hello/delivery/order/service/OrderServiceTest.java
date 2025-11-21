package hello.delivery.order.service;

import static hello.delivery.user.infrastructure.UserRole.CUSTOMER;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;

import hello.delivery.mock.FakeFinder;
import hello.delivery.mock.FakeOrderRepository;
import hello.delivery.mock.FakeProductRepository;
import hello.delivery.mock.FakeStoreRepository;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.order.controller.port.OrderService;
import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderCreate;
import hello.delivery.order.domain.OrderProductRequest;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.store.service.StoreServiceImpl;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    private OrderService orderService;
    private FakeFinder fakeFinder;
    private FakeProductRepository fakeProductRepository;

    private static final int PRODUCT_PRICE = 20000;
    private static final int ORDER_QUANTITY = 2;

    private User customer;
    private Store store;
    private Product product;

    @BeforeEach
    void setUp() {
        FakeOrderRepository fakeOrderRepository = new FakeOrderRepository();
        fakeFinder = new FakeFinder();
        fakeProductRepository = new FakeProductRepository();
        TestClockHolder testClockHolder = new TestClockHolder();
        StoreServiceImpl storeService = new StoreServiceImpl(new FakeStoreRepository(), fakeFinder, testClockHolder);
        orderService = new OrderServiceImpl(fakeOrderRepository, fakeProductRepository, storeService, fakeFinder, testClockHolder);

        setUpTestData();
    }

    private void setUpTestData() {
        User owner = buildOwner();
        customer = buildUser();
        store = buildStore(owner);
        product = buildProduct(store);
    }

    @Test
    @DisplayName("주문을 생성할 수 있다.")
    void order() {
        // given
        OrderCreate orderCreate = createOrderCreate(store, product, ORDER_QUANTITY);

        // when
        Order order = orderService.order(customer.getId(), orderCreate);

        // then
        assertThat(order.getOrderProducts()).hasSize(1);
        assertThat(order.getOrderProducts().get(0).getProduct().getName()).isEqualTo("치킨");
        assertThat(order.getOrderProducts().get(0).getQuantity()).isEqualTo(ORDER_QUANTITY);
        assertThat(order.getTotalPrice()).isEqualTo(PRODUCT_PRICE * ORDER_QUANTITY);
    }

    @Test
    @DisplayName("사용자 아이디로 주문 내역을 조회할 수 있다.")
    void findOrdersByUserId() {
        // given
        OrderCreate orderCreate = createOrderCreate(store, product, ORDER_QUANTITY);
        orderService.order(customer.getId(), orderCreate);

        // when
        List<Order> result = orderService.findOrdersByUserId(customer.getId());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getName()).isEqualTo("김우섭");
        assertThat(result.get(0).getStore().getName()).isEqualTo("BBQ");
        assertThat(result.get(0).getTotalPrice()).isEqualTo(PRODUCT_PRICE * ORDER_QUANTITY);
    }

    private OrderCreate createOrderCreate(Store store, Product product, int quantity) {
        OrderProductRequest orderProduct = OrderProductRequest.builder()
                .productName(product.getName())
                .quantity(quantity)
                .build();

        return OrderCreate.builder()
                .storeName(store.getName())
                .orderProducts(List.of(orderProduct))
                .build();
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
        fakeFinder.addUser(owner);
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
                .price(PRODUCT_PRICE)
                .store(store)
                .build();
        Product savedProduct = fakeProductRepository.save(product);
        fakeFinder.addProduct(savedProduct);
        return savedProduct;
    }
}
