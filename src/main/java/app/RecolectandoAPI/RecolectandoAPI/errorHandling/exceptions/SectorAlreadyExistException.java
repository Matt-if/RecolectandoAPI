package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SectorAlreadyExistException extends RuntimeException {
    private final String message = "El sector ya existe!";
}

