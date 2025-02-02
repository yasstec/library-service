package com.test.library_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Getter
@AllArgsConstructor
public class User {

    private final UUID id;
    private String name;

    public User(UUID id) {
        this.id = id;
    }

    public User(String name) {
        id = UUID.randomUUID();
        this.name = name;
    }
}