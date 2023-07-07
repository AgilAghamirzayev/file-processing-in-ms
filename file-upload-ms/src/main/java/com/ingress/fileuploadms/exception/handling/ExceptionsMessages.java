package com.ingress.fileuploadms.exception.handling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionsMessages {

    UNEXPECTED_EXCEPTION("Unexpected exception occurred"),
    RESOURCE_NOT_FOUND_EXCEPTION("Resource not found exception occurred"),
    FORBIDDEN_ACCESS_EXCEPTION("Access denied exception occurred"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION("Method argument not valid exception occurred");

    private final String message;
}
