package dev.efrei.openefrei.managers.users;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	  private UserRepository userRepository;

	  public UserService(UserRepository userRepository) {
	    this.userRepository = userRepository;
	  }

	  public UserEntity findByEfreiID(String efreiID) {
	    return userRepository.findByEfreiID(efreiID);
	  }

	  public UserEntity save(UserEntity user) {
	    return userRepository.save(user);
	  }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserEntity user = userRepository.findByEfreiID(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails usr = User.withUsername(user.getEfreiID()).password(user.getPassword()).authorities("USER").build();
		return usr;
	}
	
}
