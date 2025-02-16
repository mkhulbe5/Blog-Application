package com.blogapplication.payloads;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private String title;
	private String content;
	private Date createdOn;
	private String imageName;
	private UserDto user;
	private CategoryDto category;
}
