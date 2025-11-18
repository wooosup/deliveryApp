package hello.delivery.store.service;

import static hello.delivery.store.infrastructure.StoreType.JAPANESE_FOOD;
import static hello.delivery.store.infrastructure.StoreType.KOREAN_FOOD;
import static hello.delivery.user.infrastructure.UserRole.CUSTOMER;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.StoreException;
import hello.delivery.mock.FakeFinder;
import hello.delivery.mock.FakeStoreRepository;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.store.domain.Store;
import hello.delivery.store.domain.StoreCreate;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreServiceTest {

    private StoreService storeService;
    private FakeFinder fakeFinder;

    @BeforeEach
    void setUp() {
        FakeStoreRepository fakeStoreRepository = new FakeStoreRepository();
        fakeFinder = new FakeFinder();
        TestClockHolder testClockHolder = new TestClockHolder();
        storeService = new StoreService(fakeStoreRepository, fakeFinder, testClockHolder);
    }

    @Test
    @DisplayName("가게를 생성할 수 있다.")
    void create() throws Exception {
        // given
        User owner = buildOwner();
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();

        // when
        Store store = storeService.create(storeCreate);

        // then
        assertThat(store.getName()).isEqualTo("한식당");
        assertThat(store.getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(store.getTotalSales()).isZero();
    }

    @Test
    @DisplayName("고객이 가게를 생성하면 예외를 던진다.")
    void validateCreate() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .name("고객")
                .username("customer1")
                .password("password")
                .address("서울")
                .role(CUSTOMER)
                .build();
        fakeFinder.addOwner(user);
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(user.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();

        // expect
        assertThatThrownBy(() -> storeService.create(storeCreate))
                .isInstanceOf(StoreException.class)
                .hasMessageContaining("가게를 생성할 권한이 없습니다.");
    }

    @Test
    @DisplayName("가게 타입별로 가게를 조회할 수 있다.")
    void findByStoreType() throws Exception {
        // given
        User owner = buildOwner();
        fakeFinder.addOwner(owner);
        StoreCreate storeCreate1 = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();
        StoreCreate storeCreate2 = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(JAPANESE_FOOD)
                .storeName("일식당")
                .build();
        storeService.create(storeCreate1);
        storeService.create(storeCreate2);

        // when
        List<Store> stores = storeService.findByStoreType(KOREAN_FOOD);

        // then
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(stores.get(0).getName()).isEqualTo("한식당");
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

}