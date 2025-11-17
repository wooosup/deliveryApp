package hello.delivery.store.domain;

import static hello.delivery.store.infrastructure.StoreType.KOREAN_FOOD;
import static org.assertj.core.api.Assertions.assertThat;

import hello.delivery.mock.TestClockHolder;
import hello.delivery.owner.domain.Owner;
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

    private static Owner buildOwner() {
        return Owner.builder()
                .id(1L)
                .name("우섭이")
                .password("3454")
                .build();
    }

}