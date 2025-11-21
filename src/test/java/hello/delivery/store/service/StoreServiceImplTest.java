package hello.delivery.store.service;

import static hello.delivery.store.infrastructure.StoreType.JAPANESE_FOOD;
import static hello.delivery.store.infrastructure.StoreType.KOREAN_FOOD;
import static hello.delivery.user.infrastructure.UserRole.CUSTOMER;
import static hello.delivery.user.infrastructure.UserRole.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.delivery.common.exception.UserException;
import hello.delivery.mock.FakeFinder;
import hello.delivery.mock.FakeStoreRepository;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.store.controller.port.StoreService;
import hello.delivery.store.domain.Store;
import hello.delivery.store.domain.StoreCreate;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreServiceImplTest {

    private StoreService storeService;
    private FakeFinder fakeFinder;

    private User owner;

    @BeforeEach
    void setUp() {
        FakeStoreRepository fakeStoreRepository = new FakeStoreRepository();
        fakeFinder = new FakeFinder();
        TestClockHolder testClockHolder = new TestClockHolder();
        storeService = new StoreServiceImpl(fakeStoreRepository, fakeFinder, testClockHolder);

        owner = buildOwner();
    }

    @Test
    @DisplayName("가게를 생성할 수 있다.")
    void create() {
        // given
        StoreCreate storeCreate = createStoreCreate("한식당", KOREAN_FOOD);

        // when
        Store store = storeService.create(owner.getId(), storeCreate);

        // then
        assertThat(store.getName()).isEqualTo("한식당");
        assertThat(store.getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(store.getTotalSales()).isZero();
    }

    @Test
    @DisplayName("고객이 가게를 생성하면 예외를 던진다.")
    void validateCreate() {
        // given
        User customer = buildCustomer();
        StoreCreate storeCreate = createStoreCreate("한식당", KOREAN_FOOD);

        // expect
        assertThatThrownBy(() -> storeService.create(customer.getId(), storeCreate))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    @Test
    @DisplayName("가게 타입별로 가게를 조회할 수 있다.")
    void findByStoreType() {
        // given
        StoreCreate koreanStore = createStoreCreate("한식당", KOREAN_FOOD);
        StoreCreate japaneseStore = createStoreCreate("일식당", JAPANESE_FOOD);
        storeService.create(owner.getId(), koreanStore);
        storeService.create(owner.getId(), japaneseStore);

        // when
        List<Store> stores = storeService.findByStoreType(KOREAN_FOOD);

        // then
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(stores.get(0).getName()).isEqualTo("한식당");
    }

    private StoreCreate createStoreCreate(String storeName, StoreType storeType) {
        return StoreCreate.builder()
                .storeName(storeName)
                .storeType(storeType)
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

    private User buildCustomer() {
        User customer = User.builder()
                .id(2L)
                .name("고객")
                .username("customer1")
                .password("password")
                .address("서울")
                .role(CUSTOMER)
                .build();
        fakeFinder.addUser(customer);
        return customer;
    }

}
