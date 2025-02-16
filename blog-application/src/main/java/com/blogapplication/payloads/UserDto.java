package com.blogapplication.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4,message = "username must be of 4 character")
	private String userName;

	@NotEmpty
	@Size(min = 6, message = "password must be of atlead 6 characters")
	private String password;

	@NotEmpty
	@Email(message = "enter a valid email")
	private String email;

	@NotEmpty
	private String about;
}
