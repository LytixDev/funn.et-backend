package edu.ntnu.idatt2105.placeholder.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ntnu.idatt2105.placeholder.model.user.Role;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.repository.user.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

        
    @Test
    public void testFindByUsername() {
        User user = new User("username", "email", "firstName", "lastName", "password", Role.USER);
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername(user.getUsername()).get();

        assertEquals(user.getUsername(), found.getUsername());
    }

    @Test
    public void testFindByEmail() {
        User user = new User("username", "email", "firstName", "lastName", "password", Role.USER);
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByEmail(user.getEmail()).get();

        assertEquals(user.getEmail(), found.getEmail());
    }
}
