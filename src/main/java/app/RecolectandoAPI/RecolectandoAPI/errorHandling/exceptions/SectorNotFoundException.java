package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SectorNotFoundException extends RuntimeException {

  public SectorNotFoundException(Long id) {
    super("Sector no encontrado con id=" + id);
  }

  public SectorNotFoundException() {
    super("Sector no encontrado");
  }
}
