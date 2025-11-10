package hello.delivery.product.service;

import static hello.delivery.product.infrastructure.ProductSellingStatus.STOP_SELLING;
import static hello.delivery.product.infrastructure.ProductType.BEVERAGE;
import static hello.delivery.product.infrastructure.ProductType.DESSERT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.IsNotSamePassword;
import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.mock.FakeProductRepository;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.store.domain.Store;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        FakeProductRepository fakeProductRepository = new FakeProductRepository();
        productService = new ProductService(fakeProductRepository);
    }

    @Nested
    @DisplayName("해피 케이스")
    class SuccessCase {
        @Test
        @DisplayName("가게에 상품을 등록할 수 있다.")
        void create() throws Exception {
            // given
            Owner owner = buildOwner();
            Store store = buildStore(owner);
            ProductCreate productCreate = ProductCreate.builder()
                    .storeId(store.getId())
                    .type(BEVERAGE)
                    .name("아아")
                    .price(4000)
                    .build();

            // when
            Product product = productService.create(store, productCreate);

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
            Owner owner = buildOwner();
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
            List<Product> products = productService.creates(store, List.of(product1, product2));

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
        @DisplayName("상품의 판매 상태를 변경할 수 있다.")
        void changeSellingStatus() throws Exception {
            // given
            Owner owner = buildOwner();
            Store store = buildStore(owner);
            ProductCreate productCreate = ProductCreate.builder()
                    .storeId(store.getId())
                    .type(BEVERAGE)
                    .name("아아")
                    .price(4000)
                    .build();
            Product product = productService.create(store, productCreate);

            // when
            Product result = productService.changeSellingStatus(product.getId(), 3454, STOP_SELLING);

            // then
            assertThat(result.getProductSellingStatus()).isEqualTo(STOP_SELLING);
        }

        @Test
        @DisplayName("모든 상품을 조회할 수 있다.")
        void findAll() throws Exception {
            // given
            Owner owner = buildOwner();
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
            productService.creates(store, List.of(product1, product2));

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
            Owner owner = buildOwner();
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
            productService.creates(store, List.of(product1, product2));

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
            Owner owner = buildOwner();
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
            List<Product> products = productService.creates(store, List.of(product1, product2));
            productService.changeSellingStatus(products.get(0).getId(), 3454, STOP_SELLING);

            // when
            List<Product> result = productService.findBySelling();

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getName()).isEqualTo("치즈케이크");
        }
    }

    @Nested
    @DisplayName("예외 케이스")
    class ExceptionCase {

        @Test
        @DisplayName("존재하지 않는 상품의 판매 상태를 변경하려고 하면 예외가 발생한다.")
        void notFoundProduct() throws Exception {
            // given
            long productId = 999L;

            // expect
            assertThatThrownBy(() -> productService.changeSellingStatus(productId, 3454, STOP_SELLING))
                    .isInstanceOf(ProductNotFound.class)
                    .hasMessage("상품을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("상품 판매 상태 변경 시, 잘못된 비밀번호를 입력하면 예외가 발생한다.")
        void notSamePassword() throws Exception {
            // given
            Owner owner = buildOwner();
            Store store = buildStore(owner);
            ProductCreate productCreate = ProductCreate.builder()
                    .storeId(1L)
                    .type(BEVERAGE)
                    .name("아아")
                    .price(4000)
                    .build();
            Product product = productService.create(store, productCreate);

            // expect
            assertThatThrownBy(() -> productService.changeSellingStatus(product.getId(), 1111, STOP_SELLING))
                    .isInstanceOf(IsNotSamePassword.class)
                    .hasMessage("비밀번호가 틀립니다.");
        }

    }


    private static Owner buildOwner() {
        return Owner.builder()
                .id(1L)
                .name("우섭이")
                .password(3454)
                .build();
    }

    private static Store buildStore(Owner owner) {
        return Store.builder()
                .id(1L)
                .name("투썸")
                .owner(owner)
                .build();
    }

}