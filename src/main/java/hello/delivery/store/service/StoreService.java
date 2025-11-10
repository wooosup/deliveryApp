package hello.delivery.store.service;

import hello.delivery.common.service.port.ClockHolder;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.service.ProductService;
import hello.delivery.store.domain.Store;
import hello.delivery.store.domain.StoreCreate;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.store.service.port.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ProductService productService;
    private final FinderPort finder;
    private final ClockHolder clockHolder;

    @Transactional
    public Store create(Long ownerId, StoreCreate request) {
        Owner owner = finder.findByOwner(ownerId);

        Store store = Store.of(request, owner, clockHolder.now());
        return storeRepository.save(store);
    }

    public List<Store> findByStoreType(StoreType storeType) {
        return storeRepository.findByStoreType(storeType);
    }

    @Transactional
    public Product addProduct(Long storeId, ProductCreate request) {
        Store store = finder.findByStore(storeId);
        Product product = productService.create(store, request);

        store.addProduct(product);
        return product;
    }

}
