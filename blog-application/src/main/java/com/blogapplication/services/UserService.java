package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer userId);

	UserDto getUserById(UserDto userDto, Integer id);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);
}
