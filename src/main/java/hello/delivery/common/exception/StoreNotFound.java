package hello.delivery.common.exception;

public class StoreNotFound extends DeliveryException{

    private static final String MESSAGE = "가게를 찾을 수 없습니다.";

    public StoreNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
