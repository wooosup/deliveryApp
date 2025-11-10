package hello.delivery.store.service.port;

import hello.delivery.store.domain.Store;
import hello.delivery.store.infrastructure.StoreType;
import java.util.List;
import java.util.Optional;

public interface StoreRepository {

    Store save(Store store);

    Optional<Store> findById(Long id);

    List<Store> findByStoreType(StoreType storeType);

}
