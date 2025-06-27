package app.RecolectandoAPI.RecolectandoAPI.errorHandling;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PredeterminedErrorMsgs {

    public static ResponseEntity<ApiResponse> badRequestResponse(String e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .msg(e)
                        .build());
    }
}
