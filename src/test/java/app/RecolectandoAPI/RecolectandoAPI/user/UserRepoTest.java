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
    private UserRepo underTest;
    private String email;
    private User user;

    @BeforeEach
    void setUp() {
        //given
        email = "johndoe@gmail.com";
        user = User.builder().username(email).password("<Encoded PASSWORD>").role(Role.USER).build();

    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfUserExistsByUsername() {

        //when
        underTest.save(user);
        boolean expected = underTest.existsByUsername(email);

        //then
        assertTrue(expected);
    }

    @Test
    void itShouldCheckIfUserDoesNotExistsByUsername() {

        //when
        boolean expected = underTest.existsByUsername(email);

        //then
        assertFalse(expected);
    }
}
