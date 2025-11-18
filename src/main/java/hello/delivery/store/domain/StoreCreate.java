package hello.delivery.store.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.store.infrastructure.StoreType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreCreate {

    @NotNull(message = "가게 주인은 필수 입력 값입니다.")
    private final long ownerId;

    @NotBlank(message = "가게이름은 필수 입력 값입니다.")
    private final String storeName;

    @NotNull(message = "가게타입은 필수 입력 값입니다.")
    private final StoreType storeType;

    private final List<ProductCreate> products;

    @Builder
    private StoreCreate(
            @JsonProperty("ownerId") long ownerId,
            @JsonProperty("storeName") String storeName,
            @JsonProperty("storeType") StoreType storeType,
            @JsonProperty("products") List<ProductCreate> products) {
        this.ownerId = ownerId;
        this.storeName = storeName;
        this.storeType = storeType;
        this.products = products;
    }

}
