package hello.delivery.store.domain;

import hello.delivery.product.domain.ProductCreate;
import hello.delivery.store.infrastructure.StoreType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreCreate {

    private final long ownerId;
    private final String storeName;
    private final StoreType storeType;
    private final List<ProductCreate> products;

    @Builder
    private StoreCreate(long ownerId, String storeName, StoreType storeType, List<ProductCreate> products) {
        this.ownerId = ownerId;
        this.storeName = storeName;
        this.storeType = storeType;
        this.products = products;
    }

}
