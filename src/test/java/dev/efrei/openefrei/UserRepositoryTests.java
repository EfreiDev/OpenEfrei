package dev.efrei.openefrei;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import dev.efrei.openefrei.managers.users.UserEntity;
import dev.efrei.openefrei.managers.users.UserRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository repo;
    
    @Test
    public void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setAdmin(false);
        user.setEmail("john.smith@team.openefrei.test");
        user.setFirstName("John");
        user.setPassword("lorem");
        user.setLastName("Doe");
        user.setEfreiID("20280000");
        UserEntity savedUser = repo.save(user);
         
        UserEntity existUser = entityManager.find(UserEntity.class, savedUser.getID());
         
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
         
    }
}