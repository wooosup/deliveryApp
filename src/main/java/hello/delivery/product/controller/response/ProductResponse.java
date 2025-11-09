package hello.delivery.product.controller.response;

import hello.delivery.product.domain.Product;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.store.controller.response.StoreResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final StoreResponse store;
    private final ProductType type;
    private final ProductSellingStatus status;

    @Builder
    private ProductResponse(Long id, StoreResponse store, String name, int price, ProductType type, ProductSellingStatus status) {
        this.id = id;
        this.store = store;
        this.name = name;
        this.price = price;
        this.type = type;
        this.status = status;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .store(StoreResponse.of(product.getStore()))
                .name(product.getName())
                .price(product.getPrice())
                .type(product.getProductType())
                .status(product.getProductSellingStatus())
                .build();
    }
}
