package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRequest request) {
        ApiResponse answer = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
}
