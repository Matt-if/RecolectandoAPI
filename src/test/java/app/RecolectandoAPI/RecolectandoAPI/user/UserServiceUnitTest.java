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

    private String username, pass;
    private Long id;
    private Role role;
    private UserRequest userRequest;
    private User savedUser;
    private UserResponse result;

    @BeforeEach
    void setUp() {
        username = "johndoe@gmail.com";
        pass = "<PASSWORD>";
        id = 1L;
        role = Role.USER;
        userRequest = UserRequest.builder()
                .username(username)
                .password(pass)
                .build();
    }

    @Test
    void createUserTest_OK() {
        // Arrange
        when(userRepo.existsByUsername(username)).thenReturn(false);

        savedUser = User.builder()
                .id(id)
                .username(username)
                .password(pass)
                .role(role)
                .build();

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // When
        result = userService.createUser(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(role, result.getRole());
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

