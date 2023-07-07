package com.ingress.fileuploadms.exception.handling;

import com.ingress.fileuploadms.exception.ResourceNotFoundException;
import com.ingress.fileuploadms.exception.ForbiddenAccessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.ingress.fileuploadms.exception.handling.ExceptionsMessages.*;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        ExceptionMessage errorResponse = getErrorResponse(ex, UNEXPECTED_EXCEPTION);

        log.error("Exception: ", ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        ExceptionMessage errorResponse = getErrorResponse(ex, METHOD_ARGUMENT_NOT_VALID_EXCEPTION);

        log.error("MethodArgumentNotValidException: ", ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionMessage handleException(ResourceNotFoundException ex) {
        ExceptionMessage errorResponse = getErrorResponse(ex, RESOURCE_NOT_FOUND_EXCEPTION);

        log.error("FileNotFoundException: ", ex);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ForbiddenAccessException.class)
    public ExceptionMessage handleException(ForbiddenAccessException ex) {
        ExceptionMessage errorResponse = getErrorResponse(ex, FORBIDDEN_ACCESS_EXCEPTION);

        log.error("FileNotFoundException: ", ex);

        return errorResponse;
    }

    private static ExceptionMessage getErrorResponse(Exception ex, ExceptionsMessages message) {
        return ExceptionMessage.builder()
                .message(message.getMessage())
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .build();
    }
}
