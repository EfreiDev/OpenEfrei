package dev.efrei.openefrei.controllers.v1;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.managers.security.jwt.Auth;
import dev.efrei.openefrei.managers.users.UserEntity;
import dev.efrei.openefrei.managers.users.UserService;
import dev.efrei.openefrei.utils.JwtUtils;
import dev.efrei.openefrei.utils.Response;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
	private UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	public AuthController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
	}

	@RequestMapping("/v1/auth/register")
	public String register(@RequestBody UserEntity user) {
		if (userService.findByEfreiID(user.getEfreiID()) == null && userService.findByEmail(user.getEmail()) == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
			user.setPassword(encoder.encode(user.getPassword()));
			userService.save(user);
			return Response.get(200, "Success, user was created");
		} else {
			return Response.get(409, "User already exist");
		}
	}

	@RequestMapping("/v1/auth/login")
	public ResponseEntity<String> login(@RequestBody UserEntity loginUser) {
		UserEntity user = userService.findByEfreiID(loginUser.getEfreiID());
		if (user != null && new BCryptPasswordEncoder(10).matches(loginUser.getPassword(), user.getPassword())) {
			ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
					.body(Response.get(200, "Successfully connected"));
		} else {
			return ResponseEntity.status(404).body(Response.get(404, "User not found/Incorrect password"));
		}
	}

	@GetMapping("/v1/auth/me")
	public ResponseEntity<String> me(HttpServletRequest request) {
		String jwt = jwtUtils.getJwtFromCookies(request);
		if(new Auth(userService).isConnected(jwt)) {
			UserEntity user = userService.findByEfreiID(jwtUtils.getEfreiIDFromJwtToken(jwt));
			return ResponseEntity.status(200).body(Response.get(200, "Currently logged as " + user.getEmail()));
		} else {
			return ResponseEntity.status(401).body(Response.get(401, "You need to be logged"));
		}
	}
	
	@GetMapping("/v1/auth/logout")
	public ResponseEntity<String> logout() {
		ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(Response.get(200, "Successfully disconnected"));
	}

}
