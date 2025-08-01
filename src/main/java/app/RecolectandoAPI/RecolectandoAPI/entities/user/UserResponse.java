package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import app.RecolectandoAPI.RecolectandoAPI.DTO;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements DTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
}
