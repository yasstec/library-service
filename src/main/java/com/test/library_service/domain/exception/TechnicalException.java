package com.test.library_service.domain.exception;

public class TechnicalException extends RuntimeException {

    public TechnicalException(Exception originException) {
        super(originException);
    }
}
