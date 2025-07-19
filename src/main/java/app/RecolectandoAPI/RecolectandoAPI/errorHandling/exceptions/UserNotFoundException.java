package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundException extends RuntimeException {
    private final String message = "Usuario no encontrado!";
}
