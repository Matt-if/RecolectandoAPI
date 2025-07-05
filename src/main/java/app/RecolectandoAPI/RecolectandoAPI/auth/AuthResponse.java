package app.RecolectandoAPI.RecolectandoAPI.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String accessToken;
    String refreshToken;
    String msg;
}
