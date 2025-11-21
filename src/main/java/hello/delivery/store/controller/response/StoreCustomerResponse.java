package hello.delivery.store.controller.response;

import hello.delivery.store.domain.Store;
import hello.delivery.store.infrastructure.StoreType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreCustomerResponse {

    private final Long id;
    private final String name;
    private final StoreType storeType;
    private final String address;

    @Builder
    private StoreCustomerResponse(Long id, String name, StoreType storeType, String address) {
        this.id = id;
        this.name = name;
        this.storeType = storeType;
        this.address = address;
    }

    public static StoreCustomerResponse of(Store store) {
        return StoreCustomerResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .storeType(store.getStoreType())
                .address(store.getOwner().getAddress())
                .build();
    }

    public static List<StoreCustomerResponse> of(List<Store> stores) {
        return stores.stream()
                .map(StoreCustomerResponse::of)
                .toList();
    }

}
