package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) //indica que esta clase es subclase de otra y deben compararse todos los campos, incluidos los de la superclase.
@Data
public class BuildingNotFoundException extends RuntimeException {
    public BuildingNotFoundException(Long id) {
        super("Edificio no encontrado con id=" + id);
    }

    public BuildingNotFoundException() {
        super("Edificio no encontrado");
    }
}
