package hello.delivery.common.exception;

public class OrderException extends DeliveryException{

    public OrderException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
