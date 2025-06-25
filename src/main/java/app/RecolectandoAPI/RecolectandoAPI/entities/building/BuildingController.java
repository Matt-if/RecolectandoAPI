package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.ApiResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.BuildingDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
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
            Building b = buildingService.create(building);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                    .msg("Edificio creado exitosamente")
                    //.data(List.of(ToDTO.building(b)))
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
                            //.data(List.of(ToDTO.sector(sector))) //provoca error porque se intenta pasar a dto con la variable sector que no corresponde a la agregada.
                            .build()
                );
        }
        catch (Exception e) {
            return genericErrorResponse(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listAll() {
        try {
            List<DTO> list = buildingService.listAll()
                    .stream()
                    .map(ToDTO::building)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .msg("Listado de edificios exitoso")
                            .data(list)
                            .build()
            );
        } catch (Exception e) {
            return genericErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> listNamesAndId(@PathVariable Long id) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .msg("Edificio encontrado!")
                            .data(List.of(ToDTO.building(buildingService.listById(id))))
                            .build()
            );
        } catch (Exception e) {
            return genericErrorResponse(e.getMessage());
        }
    }

}
