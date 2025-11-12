package hello.delivery.common.exception;

public class ProductException extends DeliveryException{

    public ProductException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
