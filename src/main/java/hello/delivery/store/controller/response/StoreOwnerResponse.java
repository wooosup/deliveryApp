package hello.delivery.store.controller.response;

import hello.delivery.store.domain.Store;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.user.controller.response.UserResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreOwnerResponse {

    private final Long id;
    private final String name;
    private final StoreType storeType;
    private final int totalSales;
    private final UserResponse owner;

    @Builder
    private StoreOwnerResponse(Long id, String name, StoreType storeType, int totalSales, UserResponse owner) {
        this.id = id;
        this.name = name;
        this.storeType = storeType;
        this.totalSales = totalSales;
        this.owner = owner;
    }

    public static StoreOwnerResponse of(Store store) {
        return StoreOwnerResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .storeType(store.getStoreType())
                .totalSales(store.getTotalSales())
                .owner(UserResponse.of(store.getOwner()))
                .build();
    }

    public static List<StoreOwnerResponse> of(List<Store> stores) {
        return stores.stream()
                .map(StoreOwnerResponse::of)
                .toList();
    }

}
