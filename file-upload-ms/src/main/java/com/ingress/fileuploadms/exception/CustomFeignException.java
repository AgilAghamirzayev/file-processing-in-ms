package com.ingress.fileuploadms.exception;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    private Integer statusCode;

    public CustomFeignException(String errorMessage, int status) {
        super(errorMessage);
        this.statusCode = status;
    }
}
