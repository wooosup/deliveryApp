package hello.delivery.mock;

import hello.delivery.store.domain.Store;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.store.service.port.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeStoreRepository implements StoreRepository {

    private final AtomicLong autoIncrement = new AtomicLong(1);
    private final List<Store> data = new ArrayList<>();

    @Override
    public Store save(Store store) {
        if (store.getId() == null) {
            Store newStore = Store.builder()
                    .id(autoIncrement.getAndIncrement())
                    .owner(store.getOwner())
                    .storeType(store.getStoreType())
                    .name(store.getName())
                    .totalSales(store.getTotalSales())
                    .build();
            data.add(newStore);
            return newStore;
        } else {
            data.removeIf(s -> s.getId().equals(store.getId()));
            data.add(store);
            return store;
        }
    }

    @Override
    public Optional<Store> findById(Long id) {
        return data.stream()
                .filter(store -> store.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Store> findByStoreType(StoreType storeType) {
        return data.stream()
                .filter(store -> store.getStoreType().equals(storeType))
                .toList();
    }

}
