package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    @PostMapping
    public ResponseEntity<ApiResponse> createBuilding(@RequestBody Building building) {
        try {
            buildingService.create(building);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                    .msg("Edificio creado exitosamente")
                    .data(ToDTO.building(building))
                    .build()
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().msg(e.getMessage()).build());
        }
    }
}
