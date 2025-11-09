package hello.delivery.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByProductType(ProductType type);

    List<ProductEntity> findByProductSellingStatusIs(ProductSellingStatus status);

}
