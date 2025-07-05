package app.RecolectandoAPI.RecolectandoAPI.auth;

import app.RecolectandoAPI.RecolectandoAPI.entities.token.Token;
import app.RecolectandoAPI.RecolectandoAPI.entities.token.TokenRepo;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.Role;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import app.RecolectandoAPI.RecolectandoAPI.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())); //si no esta registrado, se eleva una exepcion cuyo msj es "Bad Credentials"
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();

        if (user.isDeleted()) { throw new RuntimeException("Este usuario ha sido eliminado, por favor contacte al administrador!"); }

        String token = jwtService.generateRefreshToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .msg("Inicio de sesion exitoso!")
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Email ya registrado! Elige otro por favor");
        }

        User user = buildUser(request);
        User savedUser = userRepo.save(user);
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        this.saveUserToken(savedUser, refreshToken);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .msg("Usuario registrado exitosamente!")
                .build();
    }

    private void saveUserToken(User savedUser, String token) {
        Token t = Token.builder()
                .user(savedUser)
                .token(token)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(t);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepo
                .findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
        if (!validUserTokens.isEmpty()) {
            for (Token token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepo.saveAll(validUserTokens);
        }
    }

    public AuthResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Bearer token");
        }
        String refreshToken = authHeader.substring(7);
        String username = jwtService.getUsernameFromToken(refreshToken);

        if (username == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .msg("Refresh token valido!")
                .build();
    }

    private User buildUser(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(Role.USER)
                .build();
    }
}
