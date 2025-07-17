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

        var errors = new HashMap<String, String>();
        var fieldName = "sector_name";
        errors.put(fieldName, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().msg(errors.toString()).build());
    }

    @ExceptionHandler(BuildingNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBuildingNotFound(BuildingNotFoundException exception) {

        var errors = new HashMap<String, String>();
        var fieldName = "building_id";
        errors.put(fieldName, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().msg(errors.toString()).build());
    }

    @ExceptionHandler(BuildingAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleBuildingAlreadyExistsException(BuildingAlreadyExistsException exception) {

        var errors = new HashMap<String, String>();
        var fieldName = "name";
        errors.put(fieldName, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder().msg(errors.toString()).build());
    }

    // excepciones por JSON mal formateado en el request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().msg("Error de parseo del JSON enviado, mas informacion: " + ex.getMessage()).build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> forbidden(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.builder().msg("No tienes permisos para acceder a este recurso").build());
    }

    // Cualquier error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        var errors = new HashMap<String, String>();
        var fieldName = "message";
        var errorMessage = "Ocurrio un error inesperado, consulte con el administrador o intente mas tarde.";
        errors.put(fieldName, errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errors));
    }
}


