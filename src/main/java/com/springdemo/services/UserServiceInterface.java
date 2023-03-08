package com.springdemo.services;

import com.springdemo.dto.UserDto;
import com.springdemo.entities.User;

import java.util.List;

public interface UserServiceInterface {
    void save(UserDto userDto);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<UserDto> showAll();
}
