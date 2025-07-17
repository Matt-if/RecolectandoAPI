package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorRequest;
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

    @PostMapping("/{id}/addSector")
    public ResponseEntity<ApiResponse> addSectorToBuilding(@PathVariable Long id, @Valid @RequestBody SectorRequest sR) {

        buildingService.addSector(id, sR);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .msg("Sector agregado exitosamente")
                        //.data(List.of(ToDTO.sector(sector))) //provoca error porque se intenta pasar a dto con la variable sector que no corresponde a la agregada.
                        .build()
            );

    }

    @GetMapping("/{id}/sectors")
    public ResponseEntity<ApiResponse> getSectorsFromOneBuildingById(@PathVariable Long id) {

        List<DTO> sectors = new ArrayList<>(buildingService.getSectorsFromOneBuildingById(id));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .msg("Sectores listados exitosamente")
                        .data(sectors)
                        .build()
        );
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
        BuildingResponse bR = buildingService.getBuildingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .msg("Edificio encontrado!")
                        .data(List.of(bR))
                        .build()
        );
    }
}
