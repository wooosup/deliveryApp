package hello.delivery.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import hello.delivery.product.infrastructure.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreate {

    @NotNull(message = "가게 아이디는 필수 입력 값입니다.")
    private final long storeId;

    @NotNull(message = "소유자 아이디는 필수 입력 값입니다.")
    private final long ownerId;

    @NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    private final String name;

    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    @Positive(message = "상품 가격은 양수여야 합니다.")
    private final int price;

    @NotNull(message = "상품 타입은 필수 입력 값입니다.")
    private final ProductType type;

    @Builder
    private ProductCreate(
            @JsonProperty("storeId") long storeId,
            @JsonProperty("ownerId") long ownerId,
            @JsonProperty("name") String name,
            @JsonProperty("price") int price,
            @JsonProperty("type") ProductType type) {
        this.storeId = storeId;
        this.ownerId = ownerId;
        this.name = name;
        this.price = price;
        this.type = type;
    }

}
