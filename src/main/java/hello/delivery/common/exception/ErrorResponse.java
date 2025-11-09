package hello.delivery.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

public record ErrorResponse(String code, String message, Map<String, String> validation) {

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

    public void addValidation(String field, String message) {
        this.validation.put(field, message);
    }
}
