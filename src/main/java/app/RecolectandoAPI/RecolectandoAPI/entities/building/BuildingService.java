package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorRepo;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepo buildingRepo;
    private final SectorRepo sectorRepo;

    public Building create(Building building) {
        try {
            Building b = buildingRepo.findByName(building.getName());
            if (b != null && !b.isDeleted()) {
                throw new RuntimeException("El edificio ya existe!");
            }

            if (b != null && b.isDeleted()) {
                b.setDeleted(false);
                buildingRepo.save(b);
                throw new RuntimeException("El edificio estaba eliminado, se recupero y se agrego nuevamente!");
            }

            return buildingRepo.save(building);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addSector(Long id, Sector sector) {
        try {
            Optional<Building> building = buildingRepo.findById(id);
            Sector s = sectorRepo.findByName(sector.getName());
            if (building.isEmpty()) {
                throw new RuntimeException("El edificio no existe!");
            }

            if (building.get().isSectorAlreadyAdded(s.getName()) && !s.isDeleted()) {
                throw new RuntimeException("El sector ya existe en el edificio!");
            }

            if (building.get().isSectorAlreadyAdded(s.getName()) && s.isDeleted()) {
                s.setDeleted(false);
                sectorRepo.save(s);
                throw new RuntimeException("El sector estaba eliminado, se recupero y se agrego nuevamente!");
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
            return buildingRepo.findAll().stream()
                    .filter(building -> !building.isDeleted())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar edificios: " + e.getMessage());
        }
    }

    public Building listById(Long id) {
        try {
            Building building = buildingRepo.findById(id).orElseThrow();
            if (!building.isDeleted()) return building;

            else throw new RuntimeException("El edificio se encuentra eliminado");

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Sector> getSectors(Long id) {
        try {
            return buildingRepo.findById(id).isPresent() ?
                    buildingRepo.findById(id).get().getSectors().stream().filter(s -> !s.isDeleted()).toList()
                    : null;

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
