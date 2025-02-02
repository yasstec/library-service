package com.test.library_service.domain.service;

import com.test.library_service.domain.model.User;
import com.test.library_service.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addUser(String userFullName) {
        var user = new User(userFullName);
        return userRepository.saveUser(user);
    }

}
