package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.Role;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;
    private String username;
    private User user;

    @BeforeEach
    void setUp() {
        //given
        username = "johndoe@gmail.com";
        user = User.builder().username(username).password("<Encoded PASSWORD>").role(Role.USER).build();

    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    void existsByUsernameTest() {

        //when
        userRepo.save(user);
        boolean expected = userRepo.existsByUsername(username);

        //then
        assertTrue(expected);
    }

    @Test
    void notExistsByUsernameTest() {

        //when
        boolean expected = userRepo.existsByUsername(username);

        //then
        assertFalse(expected);
    }
}
