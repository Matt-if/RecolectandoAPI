package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    private ResponseEntity<ApiResponse> genericErrorResponse(String e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().msg(e).build());
    }
    @PostMapping
    public ResponseEntity<ApiResponse> createBuilding(@RequestBody Building building) {
        try {
            buildingService.create(building);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                    .msg("Edificio creado exitosamente")
                    .data(List.of(ToDTO.building(building)))
                    .build()
            );
        }
        catch (Exception e) {
            return genericErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/{id}/addSector")
    public ResponseEntity<ApiResponse> addSector(@PathVariable Long id, @RequestBody Sector sector) {

        try {
            buildingService.addSector(id, sector);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .msg("Sector agregado exitosamente")
                            //.data(ToDTO.sector(sector)) // provoca error porque el sector es creado tarde en el proceso, y el id no se llega a setear.
                            .build()
            );
        }
        catch (Exception e) {
            return genericErrorResponse(e.getMessage());
        }
    }
}
