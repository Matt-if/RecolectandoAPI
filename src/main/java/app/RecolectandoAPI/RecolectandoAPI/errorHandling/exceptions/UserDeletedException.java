package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDeletedException extends RuntimeException {
    public UserDeletedException(String username) {
        super("El usuario " + username + " ha sido inhabilitado, por favor contacte al administrador");
    }

    public UserDeletedException() {
        super("Usuario inhabilitado");
    }
}
