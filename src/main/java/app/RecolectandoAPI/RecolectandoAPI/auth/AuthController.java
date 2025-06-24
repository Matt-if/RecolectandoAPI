package app.RecolectandoAPI.RecolectandoAPI.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        try {
            AuthResponse answer = authService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AuthResponse.builder().msg(e.getMessage()).build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse answer = authService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthResponse.builder().msg(e.getMessage()).build());
        }
    }
}
