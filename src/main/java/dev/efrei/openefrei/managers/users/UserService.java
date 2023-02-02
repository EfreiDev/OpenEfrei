package dev.efrei.openefrei.managers.users;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	  private UserRepository userRepository;

	  public UserService(UserRepository userRepository) {
	    this.userRepository = userRepository;
	  }

	  public User findByUsername(String username) {
	    return userRepository.findByUsername(username);
	  }

	  public User save(User user) {
	    return userRepository.save(user);
	  }
	
}