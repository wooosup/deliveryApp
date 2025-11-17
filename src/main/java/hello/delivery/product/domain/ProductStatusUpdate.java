package hello.delivery.product.domain;

import hello.delivery.product.infrastructure.ProductSellingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductStatusUpdate {
    private ProductSellingStatus status;
}
