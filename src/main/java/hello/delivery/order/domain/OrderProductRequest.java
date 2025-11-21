package hello.delivery.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductRequest {

    @NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    private final String productName;

    @NotNull(message = "상품 개수는 필수 입력 값입니다.")
    @Positive(message = "상품 개수는 양수여야 합니다.")
    private final int quantity;

    @Builder
    private OrderProductRequest(
            @JsonProperty("productName") String productName,
            @JsonProperty("quantity") int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }
}
