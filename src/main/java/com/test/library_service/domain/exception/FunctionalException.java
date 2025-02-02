package com.test.library_service.domain.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class FunctionalException extends RuntimeException {

    private final String key;

    private final String internalMessage;

    private final Map<String, String> messageParameters;

    protected FunctionalException(String key, String message, Map<String, String> messageParameters) {
        this.key = key;
        this.internalMessage = message;
        this.messageParameters = messageParameters;
    }

}
