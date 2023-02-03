package dev.efrei.openefrei.managers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dev.efrei.openefrei.managers.users.User;
import dev.efrei.openefrei.managers.users.UserService;
import jakarta.servlet.http.Cookie;

@Configuration
public class SecurityConfig extends WebSecurityConfiguration {

  @Autowired
  private UserService userService;
 
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @SuppressWarnings("deprecation")
@Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().requestMatchers(null, null).permitAll()
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .successHandler((request, response, authentication) -> {
          User user = userService.findByUsername(authentication.getName());

          String jwt = Jwts.builder()
            .setSubject(user.getUsername())
            .signWith(SignatureAlgorithm.HS256, "secretkey")
            .compact();

          response.addCookie(new Cookie("token", jwt));
        })
        .permitAll();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }
}
