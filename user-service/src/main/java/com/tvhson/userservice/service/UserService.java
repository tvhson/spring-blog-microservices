package com.tvhson.userservice.service;

import com.tvhson.userservice.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    UserDto updateUser(UserDto userDto, Long id);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
    boolean existsById(Long id);
}
