package hello.delivery.store.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.owner.infrastructure.OwnerEntity;
import hello.delivery.product.infrastructure.ProductEntity;
import hello.delivery.store.domain.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private OwnerEntity owner;

    private String name;

    private int dailySales;

    private int totalSales;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ProductEntity> products = new ArrayList<>();

    private LocalDate openDate;

    private LocalDate lastSalesDate;

    public static StoreEntity of(Store store) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.id = store.getId();
        storeEntity.owner = OwnerEntity.of(store.getOwner());
        storeEntity.name = store.getName();
        storeEntity.dailySales = store.getDailySales();
        storeEntity.totalSales = store.getTotalSales();
        storeEntity.storeType = store.getStoreType();
        storeEntity.openDate = store.getOpenDate();
        storeEntity.lastSalesDate = store.getLastSalesDate();
        return storeEntity;
    }

    public Store toDomain() {
        return Store.builder()
                .id(id)
                .owner(owner.toDomain())
                .name(name)
                .dailySales(dailySales)
                .totalSales(totalSales)
                .storeType(storeType)
                .products(products.stream()
                        .map(ProductEntity::toDomain)
                        .toList())
                .openDate(openDate)
                .lastSalesDate(lastSalesDate)
                .build();
    }

}
