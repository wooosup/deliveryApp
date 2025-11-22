package hello.delivery.product.infrastructure;

import hello.delivery.product.domain.ProductSellingStatus;
import hello.delivery.product.domain.ProductType;
import hello.delivery.store.infrastructure.StoreEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByStoreIdAndProductType(Long storeId, ProductType productType);

    List<ProductEntity> findByStoreIdAndProductSellingStatus(Long storeId, ProductSellingStatus productSellingStatus);

    Optional<ProductEntity> findByStoreAndName(StoreEntity store, String name);

    List<ProductEntity> findByStore(StoreEntity store);

    Optional<ProductEntity> findByName(String name);
}
