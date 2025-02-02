package com.test.library_service.domain.exception;

import java.util.Map;
import java.util.UUID;

public class ObjectNotFoundException extends FunctionalException {
    public ObjectNotFoundException(String id) {
        super("library.error.object-not-found", "Object with id ${id} not found.", Map.of("id", id));
    }

    public ObjectNotFoundException(UUID id) {
        this(id.toString());
    }
}
