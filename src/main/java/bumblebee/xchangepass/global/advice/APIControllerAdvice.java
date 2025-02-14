package bumblebee.xchangepass.global.advice;

import bumblebee.xchangepass.global.error.ErrorResponse;
import bumblebee.xchangepass.global.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class APIControllerAdvice {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> commonException(CommonException e) {
        HttpStatus statusCode = e.getErrorCode().getStatus(); // 에러코드 W001-3 익셉션 ?
        ErrorResponse response = ErrorResponse.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse.ErrorValidation> exceptionHandler(MethodArgumentNotValidException e) {

        ErrorResponse.ErrorValidation response = ErrorResponse.ErrorValidation.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();
        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(response);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(IllegalArgumentException e) {

        Map<String, String> messages = new HashMap<>();
        messages.put("message", e.getMessage());

        return ResponseEntity.status(400).body(messages);

    }

}
