package dev.efrei.openefrei.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import dev.efrei.openefrei.managers.users.UserService;
import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Resource
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		// using bCryptPasswordEncoder()
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		provider.setUserDetailsService(userService);
		return provider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/*
		 * http .authorizeHttpRequests((requests) -> requests
		 * .requestMatchers("/v1/auth/login", "/v1/auth/register", "/",
		 * "/v1/error").permitAll() .anyRequest().authenticated() ) .logout((logout) ->
		 * logout.permitAll());
		 */

		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll())
				.logout((logout) -> logout.permitAll());
		return http.build();
	}
}