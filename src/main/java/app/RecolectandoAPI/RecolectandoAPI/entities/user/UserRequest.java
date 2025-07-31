package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id; // para cuando se quiera editar los datos de un usuario, se necesita recibir su id.
    @Email(message = "formato de email debe ser ejemplo@mail.com")
    private String username;
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    private String firstname;
    private String lastname;
}
