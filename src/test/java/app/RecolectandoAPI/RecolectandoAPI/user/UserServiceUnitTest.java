package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.*;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.EmailAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepo userRepo;

    // Realish implementation to avoid mocking the mapper
    @Spy
    private UserMapper userMapper = new UserMapper(new BCryptPasswordEncoder());

    @InjectMocks
    private UserService userService;

    private String username;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        username = "johndoe@gmail.com";
        userRequest = UserRequest.builder()
                .username(username)
                .password("<PASSWORD>")
                .build();
    }

    @Test
    void createUserTest_OK() {
        // Arrange
        when(userRepo.existsByUsername(username)).thenReturn(false);

        User savedUser = User.builder()
                .id(1L)
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .role(Role.USER)
                .build();

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // When
        UserResponse result = userService.createUser(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getUsername(), result.getUsername());
        assertEquals(savedUser.getRole(), result.getRole());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void createUserTest_ThrowsEmailAlreadyRegisteredException() {
        // Arrange
        when(userRepo.existsByUsername(username)).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyRegisteredException.class,
                () -> userService.createUser(userRequest));
        verify(userRepo, never()).save(any());
    }
}

