package hello.delivery.product.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.store.infrastructure.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus productSellingStatus;

    @Builder
    private Product(Store store, String name, int price, ProductType productType, ProductSellingStatus productSellingStatus) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
    }

}
