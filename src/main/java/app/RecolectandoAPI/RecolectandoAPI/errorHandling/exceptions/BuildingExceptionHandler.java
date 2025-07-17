package app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions;

import app.RecolectandoAPI.RecolectandoAPI.errorHandling.ErrorResponse;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.GlobalExceptionHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Primary // para indicar que primero debe pasar por aqui el manejo de la excepcion y luego al global.
public class BuildingExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(BuildingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle (BuildingNotFoundException exception) {

        var errors = new HashMap<String, String>();
        var fieldName = "building_id";
        errors.put(fieldName, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errors));
    }
}
