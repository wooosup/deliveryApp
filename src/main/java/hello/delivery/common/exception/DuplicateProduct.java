package hello.delivery.common.exception;

public class DuplicateProduct extends DeliveryException{

    public DuplicateProduct(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
