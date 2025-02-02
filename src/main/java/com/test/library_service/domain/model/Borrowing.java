package com.test.library_service.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class Borrowing {
    private final UUID id;
    private final User user;
    private final Book book;
    @Setter
    private Instant borrowDate;
    @Setter
    private Instant returnDate;
}