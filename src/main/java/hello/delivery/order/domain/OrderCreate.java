package hello.delivery.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreate {

    @NotBlank(message = "가게 이름은 필수 입력 값입니다.")
    private final String storeName;

    @NotEmpty(message = "주문 상품은 필수 입력 값입니다.")
    private final List<OrderProductRequest> orderProducts;

    @Builder
    private OrderCreate(
            @JsonProperty("storeName") String storeName,
            @JsonProperty("orderProducts") List<OrderProductRequest> orderProducts) {
        this.storeName = storeName;
        this.orderProducts = orderProducts;
    }

}
