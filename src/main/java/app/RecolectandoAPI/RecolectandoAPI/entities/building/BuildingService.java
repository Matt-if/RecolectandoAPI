package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.*;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.BuildingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepo buildingRepo;
    private final SectorRepo sectorRepo;
    private final BuildingMapper mapper;
    private final SectorMapper sectorMapper;

    // Hay que controlar si recibis con id (para actualizar) y ver si existe
    // Si Viene sin id, se saltea ese checkeo obviamente
    public Building saveBuilding(BuildingRequest buildingRequest) {
       if (buildingRequest.getId() != null) {
           Building b = buildingRepo.findById(buildingRequest.getId()).orElseThrow(
                   () -> new BuildingNotFoundException("El edificio no existe!"));

           mapper.updateBuildingFromBuildingRequest(buildingRequest, b);
           return buildingRepo.save(b);
       }
       else {
           Building b = buildingRepo.findByName(buildingRequest.getName());

           if (b != null && !b.isDeleted()) {
               throw new RuntimeException("El edificio ya existe!"); //Excep. para hacer!
           }

           if (b != null) {
               b.setDeleted(false);
               buildingRepo.save(b);
               return b;
           }
       }

        return buildingRepo.save(mapper.toBuilding(buildingRequest));
    }

    public void addSector(Long id, SectorRequest sR) {

            Building b = buildingRepo.findById(id).orElseThrow(
                    () -> new BuildingNotFoundException("El edificio no existe!"));

            Sector s = sectorRepo.findByName(sR.getName());

            if (b.isSectorAlreadyAdded(s.getName()) && !s.isDeleted()) {
                throw new RuntimeException("El sector ya existe en el edificio!");
            }

            if (b.isSectorAlreadyAdded(s.getName()) && s.isDeleted()) {
                s.setDeleted(false);
                sectorRepo.save(s);
                return;
            }

            b.addSector(sectorMapper.toSector(sR, b));
            buildingRepo.save(b);
    }

    public List<BuildingResponse> listAll() {
        return buildingRepo.findAllByDeleted(false).stream()
                .map(mapper::toBuildingResponse)
                .toList();
    }

    public BuildingResponse getBuildingById(Long id) {
        Building building = buildingRepo.findById(id).orElseThrow(
                () -> new BuildingNotFoundException("El edificio no existe!"));

        if (!building.isDeleted()) return mapper.toBuildingResponse(building);

        else throw new RuntimeException("El edificio se encuentra eliminado");

    }

    public List<SectorResponse> getSectorsFromOneBuildingById(Long id) {
        Building b = buildingRepo.findById(id).orElseThrow(
                () -> new BuildingNotFoundException("El edificio no existe!"));

        return b.getSectors().stream()
                .filter(s -> !s.isDeleted())
                .map(sectorMapper::toSectorResponse)
                .toList();

    }
}
