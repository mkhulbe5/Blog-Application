package com.blogapplication.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.blogapplication.entity.User;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.UserDto;
import com.blogapplication.repositories.UserRepository;
import com.blogapplication.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RedisService redisService;

//	public static Logger log;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		if (!ObjectUtils.isEmpty(user)) {
			User savedUser = this.userRepository.save(user);
			redisService.setKey("user", savedUser, 15);
			return this.userToUserDto(savedUser);
		}
		return null;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		user.setUserName(userDto.getUserName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepository.save(user);
		return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer id) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> allUsers = this.userRepository.findAll();
		List<UserDto> userdtoList = allUsers.stream().map(user -> this.userToUserDto(user))
				.collect(Collectors.toList());
		return userdtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User userToDelete = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		this.userRepository.delete(userToDelete);
	}

	public User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setUserName(userDto.getUserName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		return user;
	}

	public UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUserName(user.getUserName());
		userDto.setPassword(user.getPassword());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		return userDto;
	}
}
