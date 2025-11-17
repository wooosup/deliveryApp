package hello.delivery.product.domain;

import hello.delivery.common.exception.ProductException;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.store.domain.Store;
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
        validateStore(productCreate, store);
        return Product.builder()
                .name(productCreate.getName())
                .price(productCreate.getPrice())
                .productType(productCreate.getType())
                .productSellingStatus(ProductSellingStatus.SELLING)
                .store(store)
                .build();
    }

    public Product changeSellingStatus(Owner owner, ProductSellingStatus status) {
        if (isNotOwner(owner)) {
            throw new ProductException("상품 상태를 변경할 권한이 없습니다.");
        }

        ProductSellingStatus newStatus = this.productSellingStatus.changeStatus(status);
        return Product.builder()
                .id(id)
                .store(store)
                .name(name)
                .price(price)
                .productType(productType)
                .productSellingStatus(newStatus)
                .build();
    }

    private static void validateStore(ProductCreate productCreate, Store store) {
        if (!store.getId().equals(productCreate.getStoreId())) {
            throw new ProductException("가게가 일치하지 않습니다.");
        }
    }

    private boolean isNotOwner(Owner owner) {
        return this.store.isNotOwner(owner);
    }

}
