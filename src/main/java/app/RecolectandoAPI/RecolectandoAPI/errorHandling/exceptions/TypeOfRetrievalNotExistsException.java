package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

public class TypeOfRetrievalNotExistsException extends RuntimeException {
    public TypeOfRetrievalNotExistsException(String type) {
        super("Tipo de residuo no existente: " + type);
    }

  public TypeOfRetrievalNotExistsException() {
    super("Tipo de residuo no existente");
  }
}
