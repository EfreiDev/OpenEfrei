package dev.efrei.openefrei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;

import dev.efrei.openefrei.managers.users.UserEntity;

@SpringBootApplication
public class OpenefreiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenefreiApplication.class, args);
		
		UserEntity test = new UserEntity();
		test.setAdmin(false);
		test.setEfreiID("efreIDlol");
		test.setEmail("salut@yo.fr");
		test.setFirstName("John");
		test.setLastName("Doe");
		test.setPassword("test");
		System.out.println(new Gson().toJson(test));
	}

}
