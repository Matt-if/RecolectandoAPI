package app.RecolectandoAPI.RecolectandoAPI.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

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
        catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse.builder().msg("Credenciales invalidas!").build());
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
