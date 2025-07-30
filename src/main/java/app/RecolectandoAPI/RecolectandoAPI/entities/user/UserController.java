package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.auth.AuthResponse;
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

    @GetMapping("/demo")
    public String demo() {
        return "this is a demo endpoint which is accessible by authenticated users";
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> saveUser(@Valid @RequestBody UserRequest request) {
        ApiResponse answer = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
}
