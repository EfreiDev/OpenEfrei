package dev.efrei.openefrei.managers.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByEfreiID(long efreiID);
}