package hello.delivery.store.service;

import static hello.delivery.product.infrastructure.ProductType.FOOD;
import static hello.delivery.store.infrastructure.StoreType.JAPANESE_FOOD;
import static hello.delivery.store.infrastructure.StoreType.KOREAN_FOOD;
import static org.assertj.core.api.Assertions.assertThat;

import hello.delivery.mock.FakeFinder;
import hello.delivery.mock.FakeProductRepository;
import hello.delivery.mock.FakeStoreRepository;
import hello.delivery.mock.TestClockHolder;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.service.ProductService;
import hello.delivery.store.domain.Store;
import hello.delivery.store.domain.StoreCreate;
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
        FakeProductRepository fakeProductRepository = new FakeProductRepository();
        ProductService productService = new ProductService(fakeProductRepository);
        fakeFinder = new FakeFinder();
        TestClockHolder testClockHolder = new TestClockHolder();
        storeService = new StoreService(fakeStoreRepository, productService, fakeFinder, testClockHolder);
    }

    @Test
    @DisplayName("owner와 StoreCreate로 Store를 생성한다.")
    void create() throws Exception {
        // given
        Owner owner = buildOwner();
        fakeFinder.addOwner(owner);
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();

        // when
        Store store = storeService.create(owner.getId(), storeCreate);

        // then
        assertThat(store.getName()).isEqualTo("한식당");
        assertThat(store.getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(store.getTotalSales()).isZero();
        assertThat(store.getProducts()).isEmpty();
    }

    private static Owner buildOwner() {
        return Owner.builder()
                .id(1L)
                .name("우섭이")
                .password(3454)
                .build();
    }

    @Test
    @DisplayName("가게 타입별로 가게를 조회할 수 있다.")
    void findByStoreType() throws Exception {
        // given
        Owner owner = buildOwner();
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
        storeService.create(owner.getId(), storeCreate1);
        storeService.create(owner.getId(), storeCreate2);

        // when
        List<Store> stores = storeService.findByStoreType(KOREAN_FOOD);

        // then
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStoreType()).isEqualTo(KOREAN_FOOD);
        assertThat(stores.get(0).getName()).isEqualTo("한식당");
    }

    @Test
    @DisplayName("가게에 상품을 추가할 수 있다.")
    void addProduct() throws Exception {
        // given
        Owner owner = buildOwner();
        fakeFinder.addOwner(owner);
        StoreCreate storeCreate = StoreCreate.builder()
                .ownerId(owner.getId())
                .storeType(KOREAN_FOOD)
                .storeName("한식당")
                .build();
        Store store = storeService.create(owner.getId(), storeCreate);
        fakeFinder.addStore(store);

        ProductCreate product = ProductCreate.builder()
                .name("치킨")
                .type(FOOD)
                .price(20000)
                .build();

        // when
        Product result = storeService.addProduct(store.getId(), product);

        // then
        assertThat(result.getName()).isEqualTo("치킨");
        assertThat(result.getPrice()).isEqualTo(20000);
        assertThat(result.getProductType()).isEqualTo(FOOD);
        assertThat(result.getStore()).isEqualTo(store);
    }


}