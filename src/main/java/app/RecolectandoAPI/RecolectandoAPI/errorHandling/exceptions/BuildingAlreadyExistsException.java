package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) //indica que esta clase es subclase de otra y deben compararse todos los campos, incluidos los de la superclase.
@Data
public class BuildingAlreadyExistsException extends RuntimeException {
    public BuildingAlreadyExistsException(String name) {
        super("El edificio: " + name + " ya existe!");
    }

    public BuildingAlreadyExistsException() {
        super("El edificio ya existe!");
    }
}
