package com.test.library_service.configuration;

import com.test.library_service.domain.port.LibraryRepository;
import com.test.library_service.domain.port.UserRepository;
import com.test.library_service.domain.service.LibraryService;
import com.test.library_service.domain.service.UserService;
import com.test.library_service.persistence.adapter.InMemoryBookRepository;
import com.test.library_service.persistence.adapter.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConfiguration {

    @Bean
    LibraryRepository libraryOutPort() {
        return new InMemoryBookRepository();
    }

    @Bean
    UserRepository userOutPort() {
        return new InMemoryUserRepository();
    }

    @Bean
    LibraryService libraryService(LibraryRepository libraryRepository, UserRepository userRepository) {
        return new LibraryService(libraryRepository, userRepository);
    }

    @Bean
    UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
