package com.marketplace.handlers;


import com.marketplace.domain.category.exceptions.CategoryNotFoundException;
import com.marketplace.domain.product.exceptions.ProductNotFoundException;
import com.marketplace.domain.user.exceptions.DuplicatedUserException;
import com.marketplace.domain.user.exceptions.UserNotFoundException;
import com.marketplace.handlers.model.ExceptionHandlerMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Matheus Alves
 */
@ControllerAdvice
public class MarketplaceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getClass().getName(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }

    @ExceptionHandler(DuplicatedUserException.class)
    public ResponseEntity<Object> handleDuplicatedUserException(
            DuplicatedUserException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getClass().getName(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException (
            CategoryNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getClass().getName(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException (
            ProductNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getClass().getName(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }
}
