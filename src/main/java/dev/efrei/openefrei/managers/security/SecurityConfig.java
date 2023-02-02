package dev.efrei.openefrei.managers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.efrei.openefrei.managers.users.User;
import dev.efrei.openefrei.managers.users.UserService;
import jakarta.servlet.http.Cookie;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
        .antMatchers("/register").permitAll()
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
