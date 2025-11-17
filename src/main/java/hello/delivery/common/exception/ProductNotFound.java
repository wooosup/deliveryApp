package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ProductNotFound extends DeliveryException{

    private static final String MESSAGE = "상품을 찾을 수 없습니다.";

    public ProductNotFound() {
        super(MESSAGE, NOT_FOUND);
    }

}
