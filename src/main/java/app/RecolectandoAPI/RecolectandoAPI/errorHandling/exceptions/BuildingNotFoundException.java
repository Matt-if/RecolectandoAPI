package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) //indica que esta clase es subclase de otra y deben compararse todos los campos, incluidos los de la superclase.
@Data
public class BuildingNotFoundException extends RuntimeException {
    private final String message = "El edificio no existe!";
}
