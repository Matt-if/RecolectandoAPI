package app.RecolectandoAPI.RecolectandoAPI.analytics;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.DTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/weight-wasteType-by-sector")
    public ResponseEntity<ApiResponse> weightOfWasteTypeBySector( @Valid
                                                                @RequestParam @NotBlank String type,
                                                                @RequestParam(required = false) @Min(2000) @Max(2025) Integer year,
                                                                @RequestParam(required = false) @Min(1) @Max(12) Integer month ) {

        List<DTO> results = analyticsService.kgOfWasteTypeBySectorYearMonth(type, year, month);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(results)
                        .msg("Datos obtenidos con exito!")
                        .build());

    }
}
