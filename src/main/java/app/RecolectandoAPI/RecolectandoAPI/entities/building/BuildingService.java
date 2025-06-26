package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.BuildingDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.ToDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.simple.internal.SimpleProvider;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepo buildingRepo;

    public Building create(Building building) {
        try {
            if (buildingRepo.existsByName((building.getName()))) {
                throw new RuntimeException("El edificio ya existe!");
            }

            return buildingRepo.save(building);
        }
        catch (Exception e) {
            throw new RuntimeException("Error al crear el edificio, mas informacion: " + e.getMessage());
        }
    }

    public void addSector(Long id, Sector sector) {
        try {
            Optional<Building> building = buildingRepo.findById(id);
            if (building.isEmpty()) {
                throw new RuntimeException("El edificio no existe!");
            }
            if (building.get().isSectorAlreadyAdded(sector.getName())) {
                throw new RuntimeException("El sector ya existe en el edificio!");
            }
            building.get().addSector(sector);
            buildingRepo.save(building.get());

        }
        catch (Exception e) {
            throw new RuntimeException("Error al agregar el sector, mas informacion: " + e.getMessage());
        }
    }

    public List<Building> listAll() {
        try {
            return buildingRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar edificios: " + e.getMessage());
        }
    }

    public Building listById(Long id) {
        try {
            return buildingRepo.findById(id).isPresent() ? buildingRepo.findById(id).get() : null;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar edificios: " + e.getMessage());
        }
    }

    public List<Sector> getSectors(Long id) {
        try {
            return buildingRepo.findById(id).isPresent() ? buildingRepo.findById(id).get().getSectors() : null;

        } catch (Exception e) {
            throw new RuntimeException("Error al listar sectores: " + e.getMessage());
        }
    }
}
