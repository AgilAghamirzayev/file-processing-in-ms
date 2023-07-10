package com.ingress.fileuploadms.exception.handling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    UNEXPECTED_EXCEPTION("Unexpected exception occurred"),
    NOT_FOUND_EXCEPTION("Resource not found exception occurred"),
    FORBIDDEN_ACCESS_EXCEPTION("Access denied exception occurred"),
    CLIENT_ERROR("Error from client");

    private final String message;
}
