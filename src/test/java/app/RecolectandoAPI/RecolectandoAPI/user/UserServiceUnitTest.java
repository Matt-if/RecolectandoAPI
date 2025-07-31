package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.*;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.EmailAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserService userService;

    // Real implementation to avoid mocking the mapper
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final UserMapper userMapper = new UserMapper(passwordEncoder);

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

        User mappedUser = userMapper.toUser(userRequest);
        User savedUser = User.builder()
                .id(1L)
                .username(username)
                .password(mappedUser.getPassword())
                .role(mappedUser.getRole())
                .build();

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse result = userService.createUser(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(mappedUser.getRole(), result.getRole());
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

