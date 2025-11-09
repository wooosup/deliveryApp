package hello.delivery.common.service;

import hello.delivery.common.exception.OwnerNotFound;
import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.common.exception.StoreNotFound;
import hello.delivery.owner.domain.Owner;
import hello.delivery.owner.service.port.OwnerRepository;
import hello.delivery.product.domain.Product;
import hello.delivery.product.service.port.ProductRepository;
import hello.delivery.store.domain.Store;
import hello.delivery.store.service.port.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Finder {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    public Product findByProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFound::new);
    }

    public Store findByStore(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(StoreNotFound::new);
    }

    public Owner findByOwner(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(OwnerNotFound::new);
    }
}
