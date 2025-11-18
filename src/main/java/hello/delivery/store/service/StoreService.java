package hello.delivery.store.service;

import hello.delivery.common.exception.StoreNotFound;
import hello.delivery.common.service.port.ClockHolder;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.store.domain.Store;
import hello.delivery.store.domain.StoreCreate;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.store.service.port.StoreRepository;
import hello.delivery.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final FinderPort finder;
    private final ClockHolder clockHolder;

    @Transactional
    public Store create(StoreCreate request) {
        User owner = finder.findByOwner(request.getOwnerId());
        Store store = Store.of(request, owner, clockHolder.now());

        return storeRepository.save(store);
    }

    public List<Store> findByStoreType(StoreType storeType) {
        return storeRepository.findByStoreType(storeType);
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findByName(String name) {
        return storeRepository.findByName(name)
                .orElseThrow(StoreNotFound::new);
    }

}
