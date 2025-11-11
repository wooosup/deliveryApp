package hello.delivery.common.exception;

public class LoginException extends DeliveryException{

    private static final String MESSAGE = "아이디 또는 비밀번호가 일치하지 않습니다.";

    public LoginException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
