package innopolis.repository;

import innopolis.model.entity.UserEntity;
import innopolis.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    private static final String TEST_USERNAME = "testuser";

    @Autowired
    public UserRepository userRepository;


    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        // Arrange
        var user = UserEntity
                .builder()
                .username(TEST_USERNAME)
                .password("password")
                .email("mail@mail.com")
                .role(UserRole.ROLE_USER_OWNER)
                .registered(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        userRepository.save(user);
        Optional<UserEntity> foundUser = userRepository.findByUsername(TEST_USERNAME);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(TEST_USERNAME);
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserNotExists() {
        Optional<UserEntity> foundUser = userRepository.findByUsername("nonexistent");
        assertThat(foundUser).isEmpty();
    }
}
