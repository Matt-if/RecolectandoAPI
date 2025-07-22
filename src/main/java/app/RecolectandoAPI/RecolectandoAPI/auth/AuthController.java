package app.RecolectandoAPI.RecolectandoAPI.auth;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        AuthResponse answer = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        AuthResponse answer = authService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    // Si el usuario recurrio a este endpoint es porque se le EXPIRO el ACCESS TOKEN
    // El refresh token debe incluirse en el header: authorization
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        try {
            AuthResponse answer = authService.refreshToken(authHeader);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse.builder().msg(e.getMessage()).build());
        }
    }
}
