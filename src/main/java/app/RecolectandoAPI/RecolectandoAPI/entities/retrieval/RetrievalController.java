package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.RetrievalDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.PredeterminedErrorMsgs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retrievals")
@RequiredArgsConstructor
public class RetrievalController {
    private final RetrievalService retrievalService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createRetrieval(@Valid @RequestBody RetrievalRequest retrievalRequest) {

        Retrieval r = retrievalService.saveRetrieval(retrievalRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .msg("Recoleccion creada exitosamente").build());

    }

    @GetMapping
    public ResponseEntity<ApiResponse> listAllRetrievals() {
        try {
            List<DTO> list = retrievalService.listAll().stream()
                    .map(ToDTO::retrieval)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .msg("Listado de recolecciones exitoso")
                            .data(list)
                    .build());
        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }
    @GetMapping("/types")
    public ResponseEntity<ApiResponse> listRetrievalTypes() {
        try {
            List<DTO> list = Arrays.stream(RetrievalType.values())
                    .map(ToDTO::retrievalType)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .msg("Listado de tipos de recoleccion exitoso")
                            .data(list)
                            .build());
        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

}
