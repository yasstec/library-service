package com.test.library_service.persistence.adapter;

import com.test.library_service.domain.model.User;
import com.test.library_service.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.*;

//TODO Should use Entity instead of domain model, but tolerated for this simple demo
@RequiredArgsConstructor
public class InMemoryUserRepository implements UserRepository {

    private final Set<User> users = new HashSet<>();

    @Override
    public User saveUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return users.stream().filter(e -> userId.equals(e.getId())).findFirst();
    }
}
