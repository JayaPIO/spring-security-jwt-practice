package com.example.spring_security_jwt.converters;

import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.models.User;

public class DtoToEntity {
    /**
     * converting UserDto into User entity
     *
     * @param userDto
     * @return User
     */
    public static User UserDtoToUserEntity(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
    }
}
