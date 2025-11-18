package hello.delivery.common.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import hello.delivery.common.exception.DeliveryException;
import hello.delivery.common.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException 발생: {}", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<ErrorResponse> deliveryException(DeliveryException e) {
        log.error("DeliveryException 발생: {}", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatus().value()))
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, e.getStatus());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse generalServerError(Exception e) {
        log.error("알 수 없는 서버 에러 발생", e);
        return ErrorResponse.builder()
                .code("500")
                .message("서버에 오류가 발생했습니다.")
                .build();
    }
}
