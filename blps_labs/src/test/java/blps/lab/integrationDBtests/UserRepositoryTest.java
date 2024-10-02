package blps.lab.integrationDBtests;

import blps.lab.auth.entity.User;
import blps.lab.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        String email = "testuser@example.com";
        User user = User.builder()
                .username(username)
                .email(email)
                .password("password")
                .build();
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername(username);

        assertThat(foundUser).isPresent();
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    public void testUpdateUser() {
        String username = "updateTest";
        User user = User.builder()
                .username(username)
                .email("updatetest@example.com")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);

        String newEmail = "newemail@example.com";
        savedUser.setEmail(newEmail);
        User updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser.getEmail()).isEqualTo(newEmail);
    }

    @Test
    public void testDeleteUser() {
        String username = "deleteTest";
        User user = User.builder()
                .username(username)
                .email("deletetest@example.com")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        assertThat(userRepository.findByUsername(username)).isEmpty();
    }
}
