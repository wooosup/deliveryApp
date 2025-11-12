package hello.delivery.product.domain;

import hello.delivery.common.exception.InvalidPasswordException;
import hello.delivery.common.exception.ProductException;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.store.domain.Store;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private final Long id;
    private final Store store;
    private final String name;
    private final int price;
    private final ProductType productType;
    private final ProductSellingStatus productSellingStatus;

    @Builder
    private Product(Long id, Store store, String name, int price, ProductType productType, ProductSellingStatus productSellingStatus) {
        this.id = id;
        this.store = store;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
    }

    public static Product of(ProductCreate productCreate, Store store) {
        Objects.requireNonNull(store, "가게는 필수입니다.");
        validateStoreOwner(productCreate, store);
        return Product.builder()
                .name(productCreate.getName())
                .price(productCreate.getPrice())
                .productType(productCreate.getType())
                .productSellingStatus(ProductSellingStatus.SELLING)
                .store(store)
                .build();
    }

    private static void validateStoreOwner(ProductCreate productCreate, Store store) {
        if (!store.getId().equals(productCreate.getStoreId())) {
            throw new ProductException("가게 주인이 일치하지 않습니다.");
        }
    }

    public Product changeSellingStatus(int password, ProductSellingStatus status) {
        if (isNotSamePassword(password)) {
            throw new InvalidPasswordException();
        }

        ProductSellingStatus product = this.productSellingStatus.changeStatus(status);
        return Product.builder()
                .id(this.id)
                .store(this.store)
                .name(this.name)
                .price(this.price)
                .productType(this.productType)
                .productSellingStatus(product)
                .build();
    }

    private boolean isNotSamePassword(int password) {
        return this.store.getOwner().getPassword() != password;
    }
}
