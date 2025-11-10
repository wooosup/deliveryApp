package hello.delivery.common.exception;

public class ProductNotFound extends DeliveryException{

    private static final String MESSAGE = "상품을 찾을 수 없습니다.";

    public ProductNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
