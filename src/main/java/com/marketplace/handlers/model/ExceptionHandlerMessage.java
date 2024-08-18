package com.marketplace.handlers.model;

import java.time.LocalDateTime;

public record ExceptionHandlerMessage(
        Integer httpStatusCode,
        String exceptionName,
        String exceptionMessage,
        LocalDateTime dateTime
) {
}
