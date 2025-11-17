package hello.delivery.store.controller.response;

import hello.delivery.owner.controller.response.OwnerResponse;
import hello.delivery.product.controller.response.ProductResponse;
import hello.delivery.store.domain.Store;
import hello.delivery.store.infrastructure.StoreType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private final Long id;
    private final String name;
    private final StoreType storeType;
    private final Integer totalSales;
    private final OwnerResponse owner;
    private final List<ProductResponse> products;

    @Builder
    private StoreResponse(Long id, String name, StoreType storeType, Integer totalSales, OwnerResponse owner,
                          List<ProductResponse> products) {
        this.id = id;
        this.name = name;
        this.storeType = storeType;
        this.totalSales = totalSales;
        this.owner = owner;
        this.products = products;
    }

    public static StoreResponse of(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .storeType(store.getStoreType())
                .totalSales(store.getTotalSales())
                .owner(OwnerResponse.of(store.getOwner()))
                .products(store.getProducts().stream()
                        .map(ProductResponse::of)
                        .toList())
                .build();
    }

    public static List<StoreResponse> of(List<Store> stores) {
        return stores.stream()
                .map(StoreResponse::of)
                .toList();
    }

}
