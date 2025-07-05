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

        //si no esta registrado, se eleva una excepcion
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();

        if (user.isDeleted()) { throw new RuntimeException("Este usuario ha sido inhabilitado, por favor contacte al administrador!"); }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user); //al revocar todos, se cierra cualquier sesion de cualquier dispositivo donde se haya logueado.
        saveUserToken(user, refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .msg("Inicio de sesion exitoso!")
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Email ya registrado! Elige otro por favor");
        }

        User user = buildUser(request);
        userRepo.save(user);

        return AuthResponse.builder()
                .msg("Usuario registrado exitosamente!")
                .build();
    }

    public AuthResponse refreshToken(String authHeader) throws Exception {

        // Extract the refresh token from the "Bearer ..." header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Bearer token");
        }
        String oldRefreshToken = authHeader.substring(7);

        // Validate & extract username
        String username = jwtService.getUsernameFromToken(oldRefreshToken);
        if (username == null) {
            throw new RuntimeException("Invalid refresh token: username claim is missing");
        }
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));

        // Verify the refresh token is valid and present in database
        Token existingToken = tokenRepo.findByToken(oldRefreshToken);
        if (existingToken == null) throw new RuntimeException("Refresh token not recognized or already used");
        if (existingToken.isExpired() || existingToken.isRevoked()) throw new RuntimeException("Refresh token is expired or revoked");

        // Invalidate the old refresh token
        existingToken.setRevoked(true);
        existingToken.setExpired(true);
        tokenRepo.save(existingToken);

        // Generate new access & refresh tokens
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        // Save new refresh token in database
        saveUserToken(user, newRefreshToken);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .msg("Refresh exitoso!")
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
                .findAllExpiredIsFalseOrRevokedIsFalseByUserId(user.getId());
        if (!validUserTokens.isEmpty()) {
            for (Token token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepo.saveAll(validUserTokens);
        }
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
