package app.RecolectandoAPI.RecolectandoAPI.user;

import app.RecolectandoAPI.RecolectandoAPI.config.SecurityConfig_TEST;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.*;
import app.RecolectandoAPI.RecolectandoAPI.jwt.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
        })
@Import(SecurityConfig_TEST.class)
public class UserControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    private String username, pass;
    private Long id;
    private Role role;
    private UserRequest userRequest, wrongUserRequest;
    private UserResponse createdUser, createdUser2;
    private User retrievedUser;

    @BeforeEach
    void setUp() {
        username = "johndoe@gmail.com";
        pass = "<PASSWORD>";
        id = 1L;
        role = Role.USER;
    }

    /*
    The ''throws Exception'' is there because your test code (using and similar tools) may throw checked exceptions,
    and it is idiomatic in unit and integration tests to allow the method to throw them so that you don’t have to clutter your test with try-catch blocks.
     */
    @Test
    public void createUserTest_OK() throws Exception {
        //Arrange
        userRequest = UserRequest.builder()
                .username(username)
                .password(pass)
                .build();

        createdUser = UserResponse.builder().id(id).username(username).role(role).build();
        when(userService.createUser(userRequest)).thenReturn(createdUser);

        // Act and Assert
        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].username").value(username))
                .andExpect(jsonPath("$.data[0].id").value(id));
    }
}
