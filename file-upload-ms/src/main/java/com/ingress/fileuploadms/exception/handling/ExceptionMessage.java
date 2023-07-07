package com.ingress.fileuploadms.exception.handling;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {

    private String error;
    private String message;
    private LocalDateTime timestamp;

}
