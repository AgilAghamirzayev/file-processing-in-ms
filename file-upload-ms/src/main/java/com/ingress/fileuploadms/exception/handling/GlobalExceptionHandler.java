package com.ingress.fileuploadms.exception.handling;

import com.ingress.fileuploadms.exception.CustomFeignException;
import com.ingress.fileuploadms.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static com.ingress.fileuploadms.exception.handling.ExceptionMessage.*;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        ErrorResponse errorResponse = getErrorResponse(ex, UNEXPECTED_EXCEPTION);

        log.error("Exception: ", ex);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handle(NotFoundException ex) {
        ErrorResponse errorResponse = getErrorResponse(ex, NOT_FOUND_EXCEPTION);

        log.error("NotFoundException: ", ex);

        return errorResponse;
    }

    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handle(CustomFeignException ex) {
        ErrorResponse errorResponse = getErrorResponse(ex, CLIENT_ERROR);

        log.error("CustomFeignException: ", ex);

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    private static ErrorResponse getErrorResponse(Exception ex, ExceptionMessage message) {
        return ErrorResponse.builder()
                .message(message.getMessage())
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .build();
    }
}
