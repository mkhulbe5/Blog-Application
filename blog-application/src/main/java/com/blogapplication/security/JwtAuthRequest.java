package com.blogapplication.security;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username;
	private String password;
}
