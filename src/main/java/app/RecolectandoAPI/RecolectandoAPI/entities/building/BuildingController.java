package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.PredeterminedErrorMsgs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    @PostMapping
    public ResponseEntity<ApiResponse> createBuilding(@Valid @RequestBody BuildingRequest buildingRequest) {
        Building b = buildingService.saveBuilding(buildingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                .msg("Edificio creado exitosamente") //podria devolverse el building entero o el id a lo sumo
                .build()
        );
    }

    @PostMapping("/{id}/sector")
    public ResponseEntity<ApiResponse> addSectorToBuilding(@PathVariable Long id, @RequestBody Sector sector) {

        try {
            buildingService.addSector(id, sector);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .msg("Sector agregado exitosamente")
                            //.data(List.of(ToDTO.sector(sector))) //provoca error porque se intenta pasar a dto con la variable sector que no corresponde a la agregada.
                            .build()
                );
        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

    @GetMapping("/{id}/sector/summary")
    public ResponseEntity<ApiResponse> listSummarySectorsFromBuilding(@PathVariable Long id) {

        try {
            List<DTO> sectors = buildingService.getSectors(id).stream()
                    .map(ToDTO::summarySector)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .msg("Sectores listados exitosamente")
                            .data(sectors)
                            .build()
            );
        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBuildings() {
        List<DTO> list = new ArrayList<>(buildingService.listAll());

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .msg("Listado de edificios exitoso")
                        .data(list)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBuildingById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .msg("Edificio encontrado!")
                        .data(List.of(buildingService.getBuildingById(id)))
                        .build()
        );
    }
}
