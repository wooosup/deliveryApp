package hello.delivery.product.infrastructure;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductSellingStatus {
    SELLING("판매중"),
    STOP_SELLING("판매중지");

    private final String text;
}
