package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailAlreadyRegisteredException extends RuntimeException{
    private final String message = "El email ya esta registrado!";
}
