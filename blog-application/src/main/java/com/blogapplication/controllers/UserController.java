package com.blogapplication.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.payloads.UserDto;
import com.blogapplication.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;

//
//	@PostMapping("/register")
//	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
//		userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encrypt password
//		userService.createUser(userDto);
//		return ResponseEntity.ok("User registered successfully!");
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
//		UserDto userDto = userService.findByEmail(email);
//		if (!passwordEncoder.matches(password, userDto.getPassword())) {
//			throw new BadCredentialsException("Invalid email or password");
//		}
//
//		String token = jwtUtil.generateToken(userDto.getUserName(), userDto.getRole());
//
//		Map<String, String> response = new HashMap<>();
//		response.put("token", token);
//		return ResponseEntity.ok(response);
//	}
//
//	@PostMapping("/create")
//	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//		UserDto user = userService.createUser(userDto);
//		log.info("user has been saved successfully");
//		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
//	}

	@PutMapping("{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updateUser = userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}

	@GetMapping("{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> allUsers = userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
}
