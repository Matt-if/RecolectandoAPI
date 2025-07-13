package app.RecolectandoAPI.RecolectandoAPI.analytics;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    //id 1 = query weight-wasteType-by-sector
    // disadvantage is that the requester should know RetrievalType enum values
    @GetMapping("/weight-wasteType-by-sector/{type}")
    public ResponseEntity<ApiResponse> weightOfWasteTypeBySector(@PathVariable RetrievalType type) {
        List<DTO> results = analyticsService.weightOfWasteTypeBySector(type).stream()
                .map(ToDTO::analyticsResult)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(results)
                        .msg("Datos obtenidos con exito!")
                        .build());

    }
}
