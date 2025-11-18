package hello.delivery.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductStatusUpdate {
    private final ProductSellingStatus status;

    @Builder
    private ProductStatusUpdate(@JsonProperty("status") ProductSellingStatus status) {
        this.status = status;
    }
}
