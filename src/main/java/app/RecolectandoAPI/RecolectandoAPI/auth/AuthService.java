package app.RecolectandoAPI.RecolectandoAPI.auth;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.Role;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import app.RecolectandoAPI.RecolectandoAPI.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())); //si no esta registrado, se eleva una exepcion cuyo msj es "Bad Credentials"
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();

        if (user.isDeleted()) { throw new RuntimeException("Este Usuario ha sido eliminado, por favor contacte al administrador para restaurarlo!"); }

        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
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
                .token(jwtService.getToken(user))
                .msg("Usuario registrado exitosamente!")
                .build();
    }

    private User buildUser(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(Role.ADMIN)
                .build();
    }
}
