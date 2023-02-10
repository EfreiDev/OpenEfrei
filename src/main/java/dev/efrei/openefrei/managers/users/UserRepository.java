package dev.efrei.openefrei.managers.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
  UserEntity findByEfreiID(String efreiID);
  UserEntity findByEmail(String email);
}