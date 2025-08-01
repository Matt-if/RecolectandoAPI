package app.RecolectandoAPI.RecolectandoAPI.errorHandling;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@Component
@RestControllerAdvice
@Slf4j // para trabajar con el logger
public class GlobalExceptionHandler {

    // Para validar campos enviados via JSON request.
    // Cuando un controller tira excepcion por el @Valid de jakarta. (ejemplo: te intentan crear un Building sin el field name)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleNotValidRequestsException(MethodArgumentNotValidException exception) {

        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            var fieldName = error.getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.warn("Validation errors: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().msg(errors.toString()).build());
    }

    //para validar parametros enviados por URL
    // Por ejemplo --> /analytics/weight-wasteType-by-sector?type=RECICLABLE?year=2000
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse> handleNotValidRequestsException(HandlerMethodValidationException exception) {
        var errors = new HashMap<String, String>();
        exception.getParameterValidationResults().forEach(result -> {
            result.getResolvableErrors().forEach(error -> {
                String[] codes = error.getCodes();
                String parameter = (codes != null && codes.length > 1) ? codes[1] : "parameter";
                String message = error.getDefaultMessage();

                errors.put(parameter, message);
            });
        });
        // For example: {type=must not be blank}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().msg(errors.toString()).build());
    }

    // Esta excepcion es lanzada cuando un parametro requerido en una URL NO es enviado!
    // Por ejemplo el type en /analytics/weight-wasteType-by-sector?type=RECICLABLE
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().msg(exception.getMessage()).build());
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


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    @ExceptionHandler(SectorAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleSectorAlreadyExists(SectorAlreadyExistException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    @ExceptionHandler(SectorNotFoundException.class)
    public ResponseEntity<ApiResponse> handleSectorAlreadyExists(SectorNotFoundException exception) {

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

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    @ExceptionHandler(UserDeletedException.class)
    public ResponseEntity<ApiResponse> handleUserDeletedException(UserDeletedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder().msg(exception.getMessage()).build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().msg(exception.getMessage()).build());
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


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.builder().msg("Credenciales invalidas --> " + ex.getMessage()).build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder().msg("Recurso no encontrado --> " + ex.getMessage()).build());
    }

    // Cualquier error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        log.error("Error: {}", ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder().msg("Ocurrio un error inesperado, consulte con el administrador o intente mas tarde.").build());
    }
}


