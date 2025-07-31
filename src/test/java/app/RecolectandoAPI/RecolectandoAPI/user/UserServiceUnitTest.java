package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserService userService;
    private String username, firstname, lastname;
    private User newUser;
    private UserRequest newUserRequest;

    @BeforeEach
    void setUp() {
        //given
        username = "johndoe@gmail.com";

    }

    @Test
    public void createUserTest() {
        // Arrange
        newUser = User.builder().username(username).password("<Encoded PASSWORD>").role(Role.USER).build();
        when(userRepo.save(newUser)).thenReturn(User.builder().username(username).password("<Encoded PASSWORD>").role(Role.USER).build());

        // Act
        newUserRequest = UserRequest.builder().username(username).password("<PASSWORD>").build();
        User createdUser = userService.createUser(newUserRequest);

        // Assert
        verify(userRepo, times(1)).save(newUser);
        // Additional assertions based on the business logic
        // e.g., assert that the created user has the expected properties
    }
}
