package dev.efrei.openefrei.managers.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dev.efrei.openefrei.managers.users.UserEntity;
import dev.efrei.openefrei.managers.users.UserService;
import dev.efrei.openefrei.utils.JwtUtils;
import dev.efrei.openefrei.utils.Response;
import jakarta.annotation.Resource;

public class Auth {

	private UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	public Auth(UserService userService) {
		this.userService = userService;
	}

	public boolean isConnected(String jwt) {
		return jwt != null && userService.findByEfreiID(jwtUtils.getEfreiIDFromJwtToken(jwt)) != null;
	}

	public UserEntity getUser(String jwt) {
		UserEntity user = userService.findByEfreiID(jwtUtils.getEfreiIDFromJwtToken(jwt));
		if(jwt != null && user != null) {
			return user;
		}
		return null;
	}

}
