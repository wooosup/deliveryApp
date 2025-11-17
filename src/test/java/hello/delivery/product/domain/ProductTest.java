package hello.delivery.product.domain;

import static hello.delivery.product.infrastructure.ProductSellingStatus.SELLING;
import static hello.delivery.product.infrastructure.ProductSellingStatus.STOP_SELLING;
import static hello.delivery.product.infrastructure.ProductType.FOOD;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.ProductException;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void create() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .name("치킨")
                .price(20000)
                .type(FOOD)
                .build();

        // when
        Product product = Product.of(productCreate, store);

        // then
        assertThat(product.getName()).isEqualTo("치킨");
        assertThat(product.getPrice()).isEqualTo(20000);
        assertThat(product.getProductType()).isEqualTo(FOOD);
        assertThat(product.getProductSellingStatus()).isEqualTo(SELLING);
    }

    @Test
    @DisplayName("상품 가격이 1원 미만이면 예외를 던진다.")
    void validatePrice() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .name("치킨")
                .price(0)
                .type(FOOD)
                .build();

        // expect
        assertThatThrownBy(() -> Product.of(productCreate, store))
                .isInstanceOf(ProductException.class)
                .hasMessageContaining("상품 가격은 양수여야 합니다.");
    }

    @Test
    @DisplayName("가게 주인이 일치하지 않으면 예외를 던진다.")
    void invalidStoreOwner() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(2L)
                .name("치킨")
                .price(20000)
                .type(FOOD)
                .build();

        // expect
        assertThatThrownBy(() -> Product.of(productCreate, store))
                .isInstanceOf(ProductException.class)
                .hasMessageContaining("가게가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("판매 상태를 변경한다.")
    void changeSellingStatus() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .name("치킨")
                .price(20000)
                .type(FOOD)
                .build();
        Product product = Product.of(productCreate, store);

        // when
        Product result = product.changeSellingStatus(owner, STOP_SELLING);

        // then
        assertThat(result.getProductSellingStatus()).isEqualTo(STOP_SELLING);
    }

    @Test
    @DisplayName("판매 상태 변경에 권한이 없으면 예외를 던진다.")
    void validateChangeSellingStatus() throws Exception {
        // given
        User owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .name("치킨")
                .price(20000)
                .type(FOOD)
                .build();
        Product product = Product.of(productCreate, store);

        User notOwner = User.builder()
                .id(2L)
                .name("ㅋㅋ")
                .password("asdasds12434")
                .role(OWNER)
                .build();

        // expect
        assertThatThrownBy(() -> product.changeSellingStatus(notOwner, STOP_SELLING))
                .isInstanceOf(ProductException.class)
                .hasMessageContaining("상품 상태를 변경할 권한이 없습니다.");
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

    private static Store buildStore(User owner) {
        return Store.builder()
                .id(1L)
                .name("BBQ")
                .owner(owner)
                .build();
    }

}