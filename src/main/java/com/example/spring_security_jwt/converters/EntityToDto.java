package com.example.spring_security_jwt.converters;

import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.models.User;

public class EntityToDto {
    /**
     * Converting User Entity into UserDto
     *
     * @param user
     * @return UserDto
     */
    public UserDto UserEntityToUserDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getRole());
    }
}

