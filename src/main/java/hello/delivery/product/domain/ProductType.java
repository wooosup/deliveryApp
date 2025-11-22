package hello.delivery.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    FOOD("음식"),
    BEVERAGE("음료"),
    DESSERT("디저트");

    private final String text;

}
