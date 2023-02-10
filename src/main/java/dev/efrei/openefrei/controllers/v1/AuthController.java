package dev.efrei.openefrei.controllers.v1;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.managers.users.UserEntity;
import dev.efrei.openefrei.managers.users.UserService;
import dev.efrei.openefrei.utils.Response;

@RestController
public class AuthController {
	private UserService userService;

	public AuthController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
	}

	@RequestMapping("/v1/auth/register")
	public String register(@RequestBody UserEntity user) {
		if (userService.findByEfreiID(user.getEfreiID()) == null && userService.findByEmail(user.getEmail()) == null) {
			userService.save(user);
			return Response.get(200, "Success, user was created");
		} else {
			return Response.get(409, "User already exist");
		}
	}

	@RequestMapping("/v1/auth/login")
	public void login(@RequestBody UserEntity loginUser) { 
		UserEntity user = userService.findByEfreiID(loginUser.getEfreiID());
		if (user != null) {
			// Create encrypted cookie and set it in response
		} else {
			// Return error
		}
	}
}
