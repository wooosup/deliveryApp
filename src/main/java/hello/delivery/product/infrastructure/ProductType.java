package hello.delivery.product.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    FOOD("음식"),
    BEVERAGE("음료");

    private final String text;

}
