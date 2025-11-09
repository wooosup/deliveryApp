package hello.delivery.product.domain;

import static hello.delivery.product.infrastructure.ProductSellingStatus.SELLING;

import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreate {

    private final long storeId;
    private final String name;
    private final int price;
    private final ProductType type;
    private final ProductSellingStatus sellingStatus;

    @Builder
    private ProductCreate(long storeId, String name, int price, ProductType type) {
        this.storeId = storeId;
        this.name = name;
        this.price = price;
        this.type = type;
        this.sellingStatus = SELLING;
    }

}
