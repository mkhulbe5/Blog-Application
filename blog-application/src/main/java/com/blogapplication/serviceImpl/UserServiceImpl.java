package com.blogapplication.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapplication.config.AppConstants;
import com.blogapplication.entity.Role;
import com.blogapplication.entity.User;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.UserDto;
import com.blogapplication.repositories.RoleRepository;
import com.blogapplication.repositories.UserRepository;
import com.blogapplication.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToUserDto(savedUser);
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
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	public UserDto userToUserDto(User user) {
		UserDto userdto = this.modelMapper.map(user, UserDto.class);
		return userdto;
	}

	@Override
	public UserDto findByEmail(String email) {
		// TODO Auto-generated method stub
		User byEmail = userRepository.findByEmail(email);
		if (byEmail != null) {
			return modelMapper.map(byEmail, UserDto.class);
		}
		return null;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.NORMALUSER).get();
		user.getRoles().add(role);
		User registeredUser = this.userRepository.save(user);
		return this.modelMapper.map(registeredUser, UserDto.class);
	}
}
