package com.blogapplication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String requestPath = request.getServletPath();
		   if (requestPath.equals("/api/v1/auth/login") || requestPath.equals("/api/v1/auth/register")) {
		        filterChain.doFilter(request, response);
		        return;
		    }
		
		String username = null;
		String token = null;

		String requestToken = request.getHeader("authorization");
		System.out.println(requestToken);

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			username = this.jwtTokenHelper.getUserNameFromToken(token);

			try {

			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("Unable to get the jwt token");
			} catch (MalformedJwtException e) {
				// TODO: handle exception
				System.out.println("Invalid jwt");
			} catch (ExpiredJwtException e) {
				System.out.println("Token expired");
			}

		} else {
			System.out.println("Invalid Token");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userByUsername = this.userDetailsService.loadUserByUsername(username);
			if (this.jwtTokenHelper.validateToken(token, userByUsername)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userByUsername, null, userByUsername.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			} else {
				System.out.println("invalid jwt");
			}
		} else {
			System.out.println("either username is null or context not found");
		}
		filterChain.doFilter(request, response);
	}

}
