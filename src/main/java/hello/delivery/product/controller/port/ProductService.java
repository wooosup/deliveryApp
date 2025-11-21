package hello.delivery.product.controller.port;

import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import java.util.List;

public interface ProductService {

    Product create(Long userId, ProductCreate request);

    List<Product> creates(Long userId, List<ProductCreate> requests);

    Product changeSellingStatus(Long id, Long userId, ProductSellingStatus status);

    void deleteById(Long ownerId, Long productId);

    List<Product> findAll();

    List<Product> findByType(Long storeId, ProductType type);

    List<Product> findBySelling(Long storeId);

    List<Product> findByStoreId(Long storeId);

}
