package hello.delivery.product.domain;

import static hello.delivery.product.infrastructure.ProductSellingStatus.SELLING;
import static hello.delivery.product.infrastructure.ProductSellingStatus.STOP_SELLING;
import static hello.delivery.product.infrastructure.ProductType.FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.InvalidPasswordException;
import hello.delivery.owner.domain.Owner;
import hello.delivery.store.domain.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("ProductCreate로 Product를 생성한다.")
    void create() throws Exception {
        // given
        Owner owner = buildOwner();
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
    @DisplayName("owner의 비밀번호를 받아 판매 상태를 변경한다.")
    void changeSellingStatus() throws Exception {
        // given
        Owner owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .name("치킨")
                .price(20000)
                .type(FOOD)
                .build();
        Product product = Product.of(productCreate, store);

        // when
        Product result = product.changeSellingStatus(3454, STOP_SELLING);

        // then
        assertThat(result.getProductSellingStatus()).isEqualTo(STOP_SELLING);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 판매 상태가 변경되지 않고 예외가 발생한다.")
    void doesNotChangeSellingStatus() throws Exception {
        // given
        Owner owner = buildOwner();
        Store store = buildStore(owner);
        ProductCreate productCreate = ProductCreate.builder()
                .storeId(store.getId())
                .name("치킨")
                .price(20000)
                .type(FOOD)
                .build();
        Product product = Product.of(productCreate, store);

        // expect
        assertThatThrownBy(() -> product.changeSellingStatus(1111, STOP_SELLING))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
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
                .name("BBQ")
                .owner(owner)
                .build();
    }

}