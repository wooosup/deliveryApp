package hello.delivery.product.infrastructure;

import hello.delivery.product.domain.Product;
import hello.delivery.product.service.port.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(ProductEntity.of(product)).toDomain();
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        List<ProductEntity> entityList = products.stream()
                .map(ProductEntity::of)
                .toList();

        return productJpaRepository.saveAll(entityList).stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByProductType(ProductType type) {
        return productJpaRepository.findByProductType(type).stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByProductSellingStatusIs(ProductSellingStatus status) {
        return productJpaRepository.findByProductSellingStatusIs(status).stream()
                .map(ProductEntity::toDomain)
                .toList();
    }
}
