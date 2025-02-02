package com.test.library_service.persistence.entity;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookEntity {

    private final UUID id;
    private String title;
    
}
