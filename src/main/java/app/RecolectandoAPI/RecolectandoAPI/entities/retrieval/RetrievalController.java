package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.RetrievalDTO;
import app.RecolectandoAPI.RecolectandoAPI.errorMsgs.PredeterminedErrorMsgs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retrievals")
@RequiredArgsConstructor
public class RetrievalController {
    private final RetrievalService retrievalService;

    //why RetrievalDTO ? --> because i request plain data, user_id instead of user for example
    @PostMapping()
    public ResponseEntity<ApiResponse> createRetrieval(@RequestBody RetrievalDTO retrievalDto) {
        try {
            Retrieval r = retrievalService.save(retrievalDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .msg("Recoleccion creada exitosamente").build());

        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

}
