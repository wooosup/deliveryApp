package hello.delivery.product.service.port;

import hello.delivery.product.domain.Product;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product save(Product product);

    List<Product> saveAll(List<Product> products);

    List<Product> findAll();

    List<Product> findByProductType(ProductType type);

    List<Product> findByProductSellingStatusIs(ProductSellingStatus status);
}
