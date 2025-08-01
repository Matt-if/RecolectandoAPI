package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.*;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.EmailAlreadyRegisteredException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    UserRepo userRepository;
    @Autowired
    UserService userService;

    private String username, pass;
    private Long id;
    private Role role;
    private UserRequest userRequest;
    private UserResponse createdUser, createdUser2;
    private User retrievedUser;

    @BeforeEach
    void setUp() {
        username = "johndoe@gmail.com";
        pass = "<PASSWORD>";
        id = 1L;
        role = Role.USER;
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    // @Sql("/data.sql")
    public void createUserTest_OK() {
        // Arrange in before each
        userRequest = UserRequest.builder()
                .username(username)
                .password(pass)
                .build();

        // Act
        createdUser = userService.createUser(userRequest);

        // Assert
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(username, createdUser.getUsername());

        // Verify the user is stored in the database
        retrievedUser = userRepository.findById(createdUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(username, retrievedUser.getUsername());
    }

    @Test
    @Order(2)
    public void createUserTest_FailedByEmailAlreadyExists() {
        // Arrange
        userRequest = UserRequest.builder()
                .username(username)
                .password(pass)
                .build();

        createdUser = userService.createUser(userRequest);

        // Act & Assert
        assertThrows(EmailAlreadyRegisteredException.class,
                () -> userService.createUser(userRequest));

    }
}
