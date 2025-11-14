package hello.delivery.product.service;

import static hello.delivery.product.infrastructure.ProductSellingStatus.SELLING;

import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.product.service.port.ProductRepository;
import hello.delivery.store.domain.Store;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product create(Store store, ProductCreate request) {
        Product product = Product.of(request, store);

        return productRepository.save(product);
    }

    @Transactional
    public List<Product> creates(Store store, List<ProductCreate> request) {
        List<Product> products = getProductList(store, request);

        return productRepository.saveAll(products);
    }

    @Transactional
    public Product changeSellingStatus(Long productId, String ownerPassword, ProductSellingStatus status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFound::new);

        Product changedProduct = product.changeSellingStatus(ownerPassword, status);
        return productRepository.save(changedProduct);
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

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    private static List<Product> getProductList(Store store, List<ProductCreate> request) {
        return request.stream()
                .map(r -> Product.of(r, store))
                .toList();
    }

}
