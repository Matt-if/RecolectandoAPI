package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.EmailAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse createUser(UserRequest request) {

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new EmailAlreadyRegisteredException();
        }

        User user = buildUser(request);
        userRepo.save(user);

        return ApiResponse.builder()
                .msg("Usuario creado exitosamente!")
                .build();
    }

    private User buildUser(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(Role.USER)
                .build();
    }

}