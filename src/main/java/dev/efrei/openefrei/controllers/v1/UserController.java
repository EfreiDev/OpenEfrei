package dev.efrei.openefrei.controllers.v1;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.managers.users.UserEntity;
import dev.efrei.openefrei.managers.users.UserService;
import dev.efrei.openefrei.utils.JwtUtils;
import dev.efrei.openefrei.utils.Response;

@RestController
public class UserController {

	private UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/v1/user/delete")
	public String delete(@RequestBody UserEntity user) {
		if (userService.findByEfreiID(user.getEfreiID()) == null && userService.findByEmail(user.getEmail()) == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
			user.setPassword(encoder.encode(user.getPassword()));
			userService.save(user);
			return Response.get(200, "Success, user was created");
		} else {
			return Response.get(409, "User already exist");
		}
	}
	
}
