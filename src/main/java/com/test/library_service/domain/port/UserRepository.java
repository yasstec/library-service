package com.test.library_service.domain.port;

import com.test.library_service.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User saveUser(User book);

    Optional<User> getUserById(UUID userId);
}