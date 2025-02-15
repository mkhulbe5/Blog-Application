package com.blogapplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class BlogApplicationTests {
	
	private RedisTemplate<String, String>  redisTemplate;

	@Test
	void contextLoads() {
	}
	
	@Test
	void testSendEmail() {
		redisTemplate.opsForValue().set("email", "mohit@gmail.com");
		String string = redisTemplate.opsForValue().get("email");
		System.out.println("checking output");
	}

}
