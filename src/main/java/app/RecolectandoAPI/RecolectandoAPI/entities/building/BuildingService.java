package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.*;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.BuildingAlreadyExistsException;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.BuildingNotFoundException;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.SectorAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
           Building b = buildingRepo.findById(buildingRequest.getId()).orElseThrow(BuildingNotFoundException::new);

           mapper.updateBuildingFromBuildingRequest(buildingRequest, b);
           return buildingRepo.save(b);
       }
       else {
           Building b = buildingRepo.findByName(buildingRequest.getName());

           if (b != null && !b.isDeleted()) {
               throw new BuildingAlreadyExistsException();
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

            Building b = buildingRepo.findById(id).orElseThrow(BuildingNotFoundException::new);

            Sector s = sectorRepo.findByName(sR.getName());

            if (s != null) {
                //Sectors names can't repeat on same building
                if (b.isSectorAlreadyAdded(s.getName()) && !s.isDeleted()) {
                    throw new SectorAlreadyExistException();
                }

                if (b.isSectorAlreadyAdded(s.getName()) && s.isDeleted()) {
                    s.setDeleted(false);
                    sectorRepo.save(s);
                    return;
                }
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
        Building building = buildingRepo.findByIdAndDeleted(id, false).orElseThrow(BuildingNotFoundException::new);

        return mapper.toBuildingResponse(building);
    }

    public List<SectorResponse> getSectorsFromOneBuildingById(Long id) {
        Building b = buildingRepo.findById(id).orElseThrow(BuildingNotFoundException::new);

        return b.getSectors().stream()
                .filter(s -> !s.isDeleted())
                .map(sectorMapper::toSectorResponse)
                .toList();

    }
}
