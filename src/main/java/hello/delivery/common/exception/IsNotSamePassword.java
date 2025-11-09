package hello.delivery.common.exception;

public class IsNotSamePassword extends DeliveryException{

    public IsNotSamePassword(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
