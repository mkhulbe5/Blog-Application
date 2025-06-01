package com.blogapplication.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtUtils {
	 @Value("${jwt.secret}")
	    private String SECRET_KEY;

	    @Value("${jwt.expiration}")
	    private long EXPIRATION_TIME;

	    public String generateToken(String username, String role) {
	        return Jwts.builder()
	                .setSubject(username)
	                .claim("role", role)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	                .compact();
	    }

	    public String extractUsername(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	    }

	    public String extractRole(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("role", String.class);
	    }

	    public boolean validateToken(String token, String username) {
	        return extractUsername(token).equals(username);
	    }
}
