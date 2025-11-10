package hello.delivery.store.domain;

import static hello.delivery.product.infrastructure.ProductType.FOOD;
import static hello.delivery.store.infrastructure.StoreType.KOREAN_FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.DuplicateProduct;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    @DisplayName("owner와 StoreCreate로 Store를 생성한다.")
    void create() throws Exception {
        // given
        LocalDate now = new TestClockHolder().now();
        Owner owner = buildOwner();
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();

        // when
        Store store = Store.of(storeCreate, owner, now);

        // then
        assertThat(store.getName()).isEqualTo("한식당");
        assertThat(store.getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(store.getTotalSales()).isZero();
    }

    @Test
    @DisplayName("총 매출과 일일 매출이 같은 날짜면 누적된다.")
    void addTotalSalesWithSameDay() throws Exception {
        // given
        LocalDate now = new TestClockHolder().now();
        Owner owner = buildOwner();
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();
        Store store = Store.of(storeCreate, owner, now);

        // when
        Store result = store.addTotalSales(25000, now);

        // then
        assertThat(result.getTotalSales()).isEqualTo(25000);
        assertThat(result.getDailySales()).isEqualTo(25000);
    }

    @Test
    @DisplayName("총 매출은 누적되고 일일 매출은 날짜가 다르면 초기화된다.")
    void addTotalSalesWithDifferentDay() throws Exception {
        // given
        LocalDate yesterday = LocalDate.of(2025, 11, 9);
        LocalDate today = LocalDate.of(2025, 11, 10);

        Owner owner = buildOwner();
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();
        Store store = Store.of(storeCreate, owner, yesterday);

        // when & then
        Store firstSale = store.addTotalSales(10000, yesterday);
        assertThat(firstSale.getTotalSales()).isEqualTo(10000);
        assertThat(firstSale.getDailySales()).isEqualTo(10000);

        Store secondSale = firstSale.addTotalSales(10000, today);
        assertThat(secondSale.getTotalSales()).isEqualTo(20000);
        assertThat(secondSale.getDailySales()).isEqualTo(10000);
        assertThat(secondSale.getLastSalesDate()).isEqualTo(today);
    }

    @Test
    @DisplayName("Store에 Product를 추가한다.")
    void addProduct() throws Exception {
        // given
        LocalDate now = new TestClockHolder().now();
        Owner owner = buildOwner();
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();
        Store store = Store.of(storeCreate, owner, now);
        Product product = Product.builder()
                .name("치킨")
                .store(store)
                .productType(FOOD)
                .price(20000)
                .build();

        // when
        store.addProduct(product);

        // then
        assertThat(store.getProducts()).hasSize(1);
        assertThat(store.getProducts().get(0).getName()).isEqualTo("치킨");
        assertThat(store.getProducts().get(0).getPrice()).isEqualTo(20000);
        assertThat(store.getProducts().get(0).getStore()).isEqualTo(store);
        assertThat(store.getProducts().get(0).getProductType()).isEqualTo(FOOD);
    }

    @Test
    @DisplayName("중복된 Product를 추가하면 예외가 발생한다.")
    void duplicateAddProduct() throws Exception {
        // given
        LocalDate now = new TestClockHolder().now();
        Owner owner = buildOwner();
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();
        Store store = Store.of(storeCreate, owner, now);
        Product product = Product.builder()
                .name("치킨")
                .store(store)
                .productType(FOOD)
                .price(20000)
                .build();
        store.addProduct(product);

        // expect
        assertThatThrownBy(() -> store.addProduct(product))
                .isInstanceOf(DuplicateProduct.class)
                .hasMessageContaining("이미 존재하는 상품입니다.");

    }


    private static Owner buildOwner() {
        return Owner.builder()
                .id(1L)
                .name("우섭이")
                .password(3454)
                .build();
    }

}