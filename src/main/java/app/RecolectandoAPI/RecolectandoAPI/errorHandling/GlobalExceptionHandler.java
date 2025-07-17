package app.RecolectandoAPI.RecolectandoAPI.errorHandling;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.BuildingAlreadyExistsException;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.BuildingNotFoundException;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.SectorAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Cuando un controller tira excepcion por el @Valid de jakarta. (ejemplo: te intentan crear un Building sin el field name)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleNotValidRequestsException(MethodArgumentNotValidException exception) {

        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            var fieldName = error.getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().msg(errors.toString()).build());
    }

    @ExceptionHandler(SectorAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleSectorAlreadyExists(SectorAlreadyExistException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    @ExceptionHandler(BuildingNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBuildingNotFound(BuildingNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    @ExceptionHandler(BuildingAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleBuildingAlreadyExistsException(BuildingAlreadyExistsException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    // excepciones por JSON mal formateado en el request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().msg("Error de parseo JSON, mas informacion: " + ex.getMessage()).build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> forbidden(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.builder().msg("No tienes permisos para acceder a este recurso").build());
    }

    // excepciones por URL mal formateada en el request
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String receivedValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";

        String msg = String.format(
                "Valor inválido para el parámetro '%s': '%s'. Se esperaba un valor de tipo %s.",
                paramName, receivedValue, expectedType
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().msg(msg).build());
    }


    // Cualquier error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder().msg("Ocurrio un error inesperado, consulte con el administrador o intente mas tarde.").build());
    }
}


