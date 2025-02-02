package com.test.library_service.api.mapper;

import com.test.library_service.domain.model.User;
import com.test.service.library_service.api.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper USER_API_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

}
