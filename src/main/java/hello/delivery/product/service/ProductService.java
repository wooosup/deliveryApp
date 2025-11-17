package hello.delivery.product.service;

import static hello.delivery.product.infrastructure.ProductSellingStatus.SELLING;

import hello.delivery.common.exception.ProductException;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.product.service.port.ProductRepository;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FinderPort finder;

    @Transactional
    public Product create(Long ownerId, ProductCreate request) {
        Store store = finder.findByStore(request.getStoreId());
        User owner = finder.findByOwner(ownerId);
        validate(store, owner);

        Product product = Product.of(request, store);

        return productRepository.save(product);
    }

    @Transactional
    public List<Product> creates(Long ownerId, List<ProductCreate> requests) {
        validateList(requests);
        Long storeId = requests.get(0).getStoreId();
        validateSameStore(requests, storeId);

        Store store = finder.findByStore(storeId);
        User owner = finder.findByOwner(ownerId);
        validate(store, owner);

        List<Product> products = getProductList(store, requests);
        return productRepository.saveAll(products);
    }

    @Transactional
    public Product changeSellingStatus(Long productId, Long ownerId, ProductSellingStatus status) {
        Product product = finder.findByProduct(productId);
        User owner = finder.findByOwner(ownerId);

        product = product.changeSellingStatus(owner, status);
        productRepository.save(product);
        return product;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByType(ProductType type) {
        return productRepository.findByProductType(type);
    }

    public List<Product> findBySelling() {
        return productRepository.findByProductSellingStatusIs(SELLING);
    }

    @Transactional
    public void deleteById(Long ownerId, Long productId) {
        Product product = finder.findByProduct(productId);
        User owner = finder.findByOwner(ownerId);
        validate(product.getStore(), owner);

        productRepository.deleteById(productId);
    }

    private static void validate(Store store, User owner) {
        if (!store.getOwner().getId().equals(owner.getId())) {
            throw new ProductException("권한이 없습니다.");
        }
    }

    private static void validateList(List<ProductCreate> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new ProductException("상품 목록이 비어 있습니다.");
        }
    }

    private static void validateSameStore(List<ProductCreate> requests, Long storeId) {
        boolean sameStore = requests.stream().allMatch(r -> storeId.equals(r.getStoreId()));
        if (!sameStore) {
            throw new ProductException("모든 상품은 동일한 매장에 속해야 합니다.");
        }
    }

    private static List<Product> getProductList(Store store, List<ProductCreate> request) {
        return request.stream()
                .map(r -> Product.of(r, store))
                .toList();
    }

}
