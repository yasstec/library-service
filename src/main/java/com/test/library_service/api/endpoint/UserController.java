package com.test.library_service.api.endpoint;

import com.test.library_service.domain.service.UserService;
import com.test.service.library_service.api.dto.*;
import com.test.service.library_service.api.endpoint.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.test.library_service.api.mapper.UserMapper.USER_API_MAPPER;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> addUser(UserCreationRequestDto userCreationRequestDto) {
        var createdUser = userService.addUser(userCreationRequestDto.getName());
        var userDto = USER_API_MAPPER.toUserDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
