package com.w2m.common;

import com.w2m.common.exceptions.ValidatioinConsult;
import com.w2m.common.exceptions.ValidationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {


    @ExceptionHandler(value = ValidationRequest.class)
    public ResponseEntity<MessageResponse> runTimeExceptionExc(ValidationRequest ex){
        MessageResponse error = MessageResponse
                .builder()
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, ex.getStatus());

    }

    @ExceptionHandler(value = ValidatioinConsult.class)
    public ResponseEntity<MessageResponse> runTimeExceptionExc(ValidatioinConsult ex){
        MessageResponse error = MessageResponse
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, ex.getStatus());

    }
}
