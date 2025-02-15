package com.blogapplication.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.blogapplication.payloads.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	
	public <T> T getKey(String key ,Class<T> entityClass) {
		try {
			Object object = redisTemplate.opsForValue().get(key);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(key,UserDto.class);
		}catch(Exception e) {
			
		}
	}
	
	public void setKey(String key ,Object o,long ttl) {
		try {
			redisTemplate.opsForValue().set(key, o, ttl);s);
		}catch(Exception e) {
			
		}
	}
	
}
