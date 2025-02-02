package com.test.library_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Getter
public class Book {

    private final UUID id;

    private String title;

    public Book(UUID id) {
        this.id = id;
    }

    public Book(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }

    public Book(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

}
