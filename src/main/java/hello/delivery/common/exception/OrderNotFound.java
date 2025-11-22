package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class OrderNotFound extends DeliveryAppException{

    private static final String MESSAGE = "주문을 찾을 수 없습니다.";

    public OrderNotFound() {
        super(MESSAGE, NOT_FOUND);
    }

}
