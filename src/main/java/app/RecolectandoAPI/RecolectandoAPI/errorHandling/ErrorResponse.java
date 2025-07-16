package app.RecolectandoAPI.RecolectandoAPI.errorHandling;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
