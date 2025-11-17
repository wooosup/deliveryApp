package hello.delivery.product.service;

import static hello.delivery.product.infrastructure.ProductSellingStatus.STOP_SELLING;
import static hello.delivery.product.infrastructure.ProductType.BEVERAGE;
import static hello.delivery.product.infrastructure.ProductType.DESSERT;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.ProductException;
import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.mock.FakeFinder;
import hello.delivery.mock.FakeProductRepository;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;
    private FakeFinder fakeFinder;

    @BeforeEach
    void setUp() {
        FakeProductRepository fakeProductRepository = new FakeProductRepository();
        fakeFinder = new FakeFinder();

        productService = new ProductService(fakeProductRepository, fakeFinder);
    }

    @Test
    @DisplayName("가게에 상품을 등록할 수 있다.")
    void create() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();

        // when
        Product product = productService.create(owner.getId(), productCreate);

        // then
        assertThat(product.getName()).isEqualTo("아아");
        assertThat(product.getPrice()).isEqualTo(4000);
        assertThat(product.getProductType()).isEqualTo(BEVERAGE);
        assertThat(product.getStore().getName()).isEqualTo("투썸");
        assertThat(product.getStore().getOwner()).isEqualTo(owner);
    }

    @Test
    @DisplayName("가게에 여러 상품을 등록할 수 있다.")
    void creates() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate product1 = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();
        ProductCreate product2 = ProductCreate.builder()
                .storeId(store.getId())
                .type(DESSERT)
                .name("치즈케이크")
                .price(5500)
                .build();

        // when
        List<Product> products = productService.creates(owner.getId(), List.of(product1, product2));

        // then
        assertThat(products.get(0).getName()).isEqualTo("아아");
        assertThat(products.get(0).getPrice()).isEqualTo(4000);
        assertThat(products.get(0).getProductType()).isEqualTo(BEVERAGE);
        assertThat(products.get(0).getStore().getName()).isEqualTo("투썸");
        assertThat(products.get(0).getStore().getOwner()).isEqualTo(owner);

        assertThat(products.get(1).getName()).isEqualTo("치즈케이크");
        assertThat(products.get(1).getPrice()).isEqualTo(5500);
        assertThat(products.get(1).getProductType()).isEqualTo(DESSERT);
        assertThat(products.get(1).getStore().getName()).isEqualTo("투썸");
        assertThat(products.get(1).getStore().getOwner()).isEqualTo(owner);
    }

    @Test
    @DisplayName("모든 상품이 같은 가게가 아니면 예외를 던진다.")
    void validateCreates() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate product1 = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();

        Store fakeStore = Store.builder()
                .id(2L)
                .name("스타벅스")
                .owner(owner)
                .build();
        fakeFinder.addStore(fakeStore);
        ProductCreate product2 = ProductCreate.builder()
                .storeId(fakeStore.getId())
                .type(DESSERT)
                .name("치즈케이크")
                .price(5500)
                .build();

        // expect
        assertThatThrownBy(() -> productService.creates(owner.getId(), List.of(product1, product2)))
                .isInstanceOf(ProductException.class)
                .hasMessageContaining("모든 상품은 동일한 매장에 속해야 합니다.");
    }

    @Test
    @DisplayName("상품의 판매 상태를 변경할 수 있다.")
    void changeSellingStatus() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();
        Product product = productService.create(owner.getId(), productCreate);
        fakeFinder.addProduct(product);

        // when
        Product result = productService.changeSellingStatus(product.getId(), owner.getId(), STOP_SELLING);

        // then
        assertThat(result.getProductSellingStatus()).isEqualTo(STOP_SELLING);
    }

    @Test
    @DisplayName("모든 상품을 조회할 수 있다.")
    void findAll() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate product1 = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();
        ProductCreate product2 = ProductCreate.builder()
                .storeId(store.getId())
                .type(DESSERT)
                .name("치즈케이크")
                .price(5500)
                .build();
        productService.creates(owner.getId(), List.of(product1, product2));

        // when
        List<Product> products = productService.findAll();

        // then
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("아아");
        assertThat(products.get(1).getName()).isEqualTo("치즈케이크");
    }

    @Test
    @DisplayName("상품 타입으로 상품을 조회할 수 있다.")
    void findByType() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate product1 = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();
        ProductCreate product2 = ProductCreate.builder()
                .storeId(store.getId())
                .type(DESSERT)
                .name("치즈케이크")
                .price(5500)
                .build();
        productService.creates(owner.getId(), List.of(product1, product2));

        // when
        List<Product> result = productService.findByType(DESSERT);

        // then
        assertThat(result.get(0).getName()).isEqualTo("치즈케이크");
        assertThat(result.get(0).getStore().getName()).isEqualTo("투썸");
    }

    @Test
    @DisplayName("판매중인 상품을 조회할 수 있다.")
    void findBySelling() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate product1 = ProductCreate.builder()
                .storeId(store.getId())
                .type(BEVERAGE)
                .name("아아")
                .price(4000)
                .build();
        ProductCreate product2 = ProductCreate.builder()
                .storeId(store.getId())
                .type(DESSERT)
                .name("치즈케이크")
                .price(5500)
                .build();
        List<Product> products = productService.creates(owner.getId(), List.of(product1, product2));
        fakeFinder.addProduct(products.get(0));
        fakeFinder.addProduct(products.get(1));

        productService.changeSellingStatus(products.get(0).getId(), owner.getId(), STOP_SELLING);

        // when
        List<Product> result = productService.findBySelling();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("치즈케이크");
    }


    @Test
    @DisplayName("존재하지 않는 상품의 판매 상태를 변경하려고 하면 예외가 발생한다.")
    void notFoundProduct() throws Exception {
        // given
        long productId = 999L;

        // expect
        assertThatThrownBy(() -> productService.changeSellingStatus(productId, null, STOP_SELLING))
                .isInstanceOf(ProductNotFound.class)
                .hasMessage("상품을 찾을 수 없습니다.");
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

    private Store buildStore(User owner) {
        Store store = Store.builder()
                .id(1L)
                .name("투썸")
                .owner(owner)
                .build();
        fakeFinder.addStore(store);
        return store;
    }


}