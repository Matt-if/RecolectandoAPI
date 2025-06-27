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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    @PostMapping
    public ResponseEntity<ApiResponse> createBuilding(@RequestBody Building building) {
        try {
            Building b = buildingService.create(building);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                    .msg("Edificio creado exitosamente")
                    //.data(List.of(ToDTO.building(b)))
                    .build()
            );
        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
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
            List<DTO> sectores = buildingService.getSectors(id).stream()
                    .map(ToDTO::summarySector)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .msg("Sectores listados exitosamente")
                            .data(sectores)
                            .build()
            );
        }
        catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

    /*
    ----- This is new for me ! -------
    Function<Building, DTO>: is a functional interface that takes a Building and returns a DTO.

    ToDTO::completeBuilding and ToDTO::summaryBuilding are both compatible with Function<Building, DTO>, so you can pass them directly.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> listAllCompleteBuildings() {
        return listAllBuildings(ToDTO::completeBuilding);
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse> listAllSummaryBuildings() {
        return listAllBuildings(ToDTO::summaryBuilding);
    }

    private ResponseEntity<ApiResponse> listAllBuildings(Function<Building, DTO> mapper) {
        try {
            List<DTO> list = buildingService.listAll()
                    .stream()
                    .map(mapper)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .msg("Listado de edificios exitoso")
                            .data(list)
                            .build()
            );
        } catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> listOneCompleteBuilding(@PathVariable Long id) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .msg("Edificio encontrado!")
                            .data(List.of(ToDTO.completeBuilding(buildingService.listById(id))))
                            .build()
            );
        } catch (Exception e) {
            return PredeterminedErrorMsgs.badRequestResponse((e.getMessage()));
        }
    }

}
