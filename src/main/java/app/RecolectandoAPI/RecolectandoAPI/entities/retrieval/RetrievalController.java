package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.DTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        List<DTO> list = retrievalService.listAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .msg("Listado de recolecciones exitoso")
                        .data(list)
                .build());
    }

    @GetMapping("/types")
    public ResponseEntity<ApiResponse> listRetrievalTypes() {
        List<DTO> list = retrievalService.listAllRetrievalTypes();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .msg("Listado de tipos de recoleccion exitoso")
                        .data(list)
                        .build());
    }

}
