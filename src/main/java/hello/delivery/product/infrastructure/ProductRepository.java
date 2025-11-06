package hello.delivery.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductType(ProductType type);

    List<Product> findByProductSellingStatusIs(ProductSellingStatus status);

}
