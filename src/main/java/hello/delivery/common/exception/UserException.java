package hello.delivery.common.exception;

public class UserException extends DeliveryException{

    public UserException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
