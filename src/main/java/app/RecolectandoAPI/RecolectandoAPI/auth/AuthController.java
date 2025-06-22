package app.RecolectandoAPI.RecolectandoAPI.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login")
    public String login() {
        return "endpoint to Login";
    }

    @PostMapping("/register")
    public void register() {

    }
}
