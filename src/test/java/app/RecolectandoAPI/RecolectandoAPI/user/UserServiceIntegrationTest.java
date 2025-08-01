package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    User user;
}
