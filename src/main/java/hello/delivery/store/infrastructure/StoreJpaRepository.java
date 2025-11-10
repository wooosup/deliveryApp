package hello.delivery.store.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<StoreEntity, Long> {

    List<StoreEntity> findByStoreType(StoreType type);

}
