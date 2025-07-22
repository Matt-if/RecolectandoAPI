package app.RecolectandoAPI.RecolectandoAPI.entities.sector;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepo extends JpaRepository<Sector, Long> {
    Sector findByName(String name);

    Sector findByNameAndBuilding_Id(String name, long building_id);
}
