package hello.delivery.store.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.product.infrastructure.Product;
import hello.delivery.owner.infrastructure.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    private String name;

    private int totalSales;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Product> products = new ArrayList<>();

    @Builder
    private Store(Owner owner, String name, StoreType storeType) {
        this.owner = owner;
        this.name = name;
        this.storeType = storeType;
        this.totalSales = 0;
    }

}
