package com.w2m.common.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatioinConsult extends RuntimeException{

    private HttpStatus status;

    public ValidatioinConsult(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
