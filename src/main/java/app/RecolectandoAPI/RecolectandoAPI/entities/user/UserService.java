package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.EmailAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {

        if (userRepo.existsByUsername(userRequest.getUsername())) {
            throw new EmailAlreadyRegisteredException();
        }

        User user = userRepo.save(userMapper.toUser(userRequest));

        return userMapper.toUserResponse(user);

    }



}