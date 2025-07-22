package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SectorAlreadyExistException extends RuntimeException {
    public SectorAlreadyExistException(String name, String buildingName) {
        super("El sector: " + name + " ya existe en el edificio: " + buildingName);
    }

    public SectorAlreadyExistException() {
        super();
    }
}

