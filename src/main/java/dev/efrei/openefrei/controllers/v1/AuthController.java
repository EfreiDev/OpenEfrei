package dev.efrei.openefrei.controllers.v1;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.managers.users.User;
import dev.efrei.openefrei.managers.users.UserService;

@RestController
public class AuthController {
  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public AuthController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("/register")
  public void register(@RequestBody User user) {
    userService.save(user);
  }

  @PostMapping("/login")
  public void login(@RequestBody User loginUser) {
    User user = userService.findByUsername(loginUser.getUsername());
    if (user != null) {
      // Create encrypted cookie and set it in response
    } else {
      // Return error
    }
  }
}
