package com.blogapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.payloads.UserDto;
import com.blogapplication.serviceImpl .RedisService;
import com.blogapplication.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;

//	public static Logger log;

	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto user = userService.createUser(userDto);
//		log.info("user has been saved successfully");
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
	}

	@PostMapping("/updateUser")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, Integer userId) {
		UserDto updateUser = userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}

	@GetMapping("{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
//		String userId1 = String.valueOf(userId);
//		if (!ObjectUtils.isEmpty(redisService)) {
//			UserDto userDto = redisService.getKey(userId1, UserDto.class);
//			return ResponseEntity.ok(userDto);
//		} else {
		return ResponseEntity.ok(userService.getUserById(userId));
	}
//	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> allUsers = userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
}
