package app.RecolectandoAPI.RecolectandoAPI.analytics;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/weight-wasteType-by-sector")
    public ResponseEntity<ApiResponse> weightOfWasteTypeBySector(
                                                                @RequestParam String type,
                                                                @RequestParam(required = false) Integer year,
                                                                @RequestParam(required = false) Integer month ) {
        try {
            if (!List.of(RetrievalType.values()).toString().contains(type))
                throw new IllegalArgumentException("Tipo de residuo no existente: " + type );

            List<DTO> results = analyticsService.kgOfWasteTypeBySectorYearMonth(RetrievalType.valueOf(type), year, month).stream()
                    .map(ToDTO::analyticsResult)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .data(results)
                            .msg("Datos obtenidos con exito!")
                            .build());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .msg(e.getMessage())
                            .build());
        }
    }
}
