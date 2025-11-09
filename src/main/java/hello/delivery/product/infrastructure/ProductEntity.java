package hello.delivery.product.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.product.domain.Product;
import hello.delivery.store.infrastructure.StoreEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreEntity store;

    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus productSellingStatus;

    public static ProductEntity of(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.id = product.getId();
        productEntity.store = StoreEntity.of(product.getStore());
        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.productType = product.getProductType();
        productEntity.productSellingStatus = product.getProductSellingStatus();
        return productEntity;
    }

    public Product toDomain() {
        return Product.builder()
                .id(id)
                .store(store.toDomain())
                .name(name)
                .price(price)
                .productType(productType)
                .productSellingStatus(productSellingStatus)
                .build();
    }
}
