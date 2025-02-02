package com.test.library_service.persistence.entity;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BorrowingEntity {
    private final UUID id;
    private final UUID userId;
    private final UUID bookId;
    private Instant borrowDate;
    private Instant returnDate;
}