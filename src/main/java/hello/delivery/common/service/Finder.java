package hello.delivery.common.service;

import hello.delivery.common.exception.UserNotFound;
import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.common.exception.StoreNotFound;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.product.domain.Product;
import hello.delivery.product.service.port.ProductRepository;
import hello.delivery.store.domain.Store;
import hello.delivery.store.service.port.StoreRepository;
import hello.delivery.user.domain.User;
import hello.delivery.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Finder implements FinderPort {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public Product findByProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFound::new);
    }

    @Override
    public Store findByStore(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(StoreNotFound::new);
    }

    @Override
    public User findByOwner(Long id) {
        return userRepository.findByOwnerId(id)
                .orElseThrow(UserNotFound::new);
    }

    @Override
    public User findByUser(Long id) {
        return userRepository.findByCustomerId(id)
                .orElseThrow(UserNotFound::new);
    }
}
