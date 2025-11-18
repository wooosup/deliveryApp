package hello.delivery.store.infrastructure;

import hello.delivery.common.exception.StoreNotFound;
import hello.delivery.store.domain.Store;
import hello.delivery.store.service.port.StoreRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final StoreJpaRepository storeJpaRepository;

    @Override
    public Store save(Store store) {
        return storeJpaRepository.save(StoreEntity.of(store)).toDomain();
    }

    @Override
    public Optional<Store> findById(Long id) {
        return storeJpaRepository.findById(id)
                .map(StoreEntity::toDomain);
    }

    @Override
    public List<Store> findByStoreType(StoreType storeType) {
        return storeJpaRepository.findByStoreType(storeType).stream()
                .map(StoreEntity::toDomain)
                .toList();
    }

    @Override
    public List<Store> findAll() {
        return storeJpaRepository.findAll().stream()
                .map(StoreEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Store> findByName(String name) {
        return storeJpaRepository.findByName(name)
                .map(StoreEntity::toDomain);
    }

    @Override
    public void updateSales(Long storeId, int dailySales, int totalSales, LocalDate lastSalesDate) {
        StoreEntity storeEntity = storeJpaRepository.findById(storeId)
                .orElseThrow(StoreNotFound::new);

        storeEntity.updateSales(dailySales, totalSales, lastSalesDate);
    }
}
