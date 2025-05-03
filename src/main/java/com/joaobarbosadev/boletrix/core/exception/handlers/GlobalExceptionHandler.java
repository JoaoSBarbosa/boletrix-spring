package com.joaobarbosadev.boletrix.core.exception.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.exception.response.StandardError;
import com.joaobarbosadev.boletrix.core.utils.Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(CustomEmptyFieldException.class)
    public ResponseEntity<Object> handleCustomEmptyFieldException(CustomEmptyFieldException exception, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        buildErrorResponse(status,exception.getMessage(),error,request);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<Object> handleCustomEntityNotFoundException(CustomEntityNotFoundException exception, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;
        buildErrorResponse(status,exception.getMessage(),error,request);
        return new ResponseEntity<>(error, status);
    }

    private void buildErrorResponse(HttpStatus status, String messageException, StandardError error, HttpServletRequest request) {
        error.setMessage(messageException);
        error.setStatus(status.value());
        error.setPath(request.getRequestURI());
        error.setTitle("Campo vazio");
        error.setTimestamp(Util.getFormattedInstance());
    }
}
