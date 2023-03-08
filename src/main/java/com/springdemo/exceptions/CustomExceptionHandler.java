package com.springdemo.exceptions;

import com.springdemo.dto.ResponseError;
import com.springdemo.exceptions.parts.CustomDuplicateError;
import com.springdemo.exceptions.parts.CustomNotFoundException;
import com.springdemo.exceptions.parts.CustomUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = CustomUnauthorizedException.class)
    public ResponseEntity<?> unauthorizedExceptionHandler(CustomUnauthorizedException e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.UNAUTHORIZED.value(), "Unauthorized bos!", null, LocalDateTime.now()
        );
        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    @ExceptionHandler(value = CustomNotFoundException.class)
    public ResponseEntity<?> notFoundExceptionHandler(CustomNotFoundException e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.NOT_FOUND.value(), "Kosong bos!", null, LocalDateTime.now()
        );
        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    @ExceptionHandler(value = CustomDuplicateError.class)
    public ResponseEntity<?> duplicateErrorHandler(CustomDuplicateError e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Udah ada bos!", null, LocalDateTime.now()
        );
        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }
}
