package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDeletedException extends RuntimeException {
    private final String message = "Este usuario ha sido inhabilitado, por favor contacte al administrador!";
}
