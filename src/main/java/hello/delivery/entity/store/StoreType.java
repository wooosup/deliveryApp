package hello.delivery.entity.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreType {
    CAFE("카페"),
    KOREAN_FOOD("한식"),
    WESTERN_FOOD("양식"),
    CHINESE_FOOD("중식"),
    JAPANESE_FOOD("일식"),
    SNACK("분식"),
    FAST_FOOD("패스트푸드"),
    DESSERT("디저트");

    private final String text;
}
