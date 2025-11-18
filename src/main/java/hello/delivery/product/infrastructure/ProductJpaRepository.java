package hello.delivery.product.infrastructure;

import hello.delivery.store.infrastructure.StoreEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByStoreIdAndProductType(Long storeId, ProductType productType);

    List<ProductEntity> findByStoreIdAndProductSellingStatus(Long storeId, ProductSellingStatus productSellingStatus);

    Optional<ProductEntity> findByStoreAndName(StoreEntity store, String name);

    List<ProductEntity> findByStore(StoreEntity store);
}
