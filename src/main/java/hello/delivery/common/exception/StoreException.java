package hello.delivery.common.exception;

public class StoreException extends DeliveryException{

    public StoreException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
