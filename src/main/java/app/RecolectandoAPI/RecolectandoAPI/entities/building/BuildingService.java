package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepo buildingRepo;

    public void create(Building building) {
        try {
            if (buildingRepo.existsByName((building.getName()))) {
                throw new RuntimeException("El edificio ya existe!");
            }

            buildingRepo.save(building);
        }
        catch (Exception e) {
            throw new RuntimeException("Error al crear el edificio, mas informacion: " + e.getMessage());
        }
    }
}
