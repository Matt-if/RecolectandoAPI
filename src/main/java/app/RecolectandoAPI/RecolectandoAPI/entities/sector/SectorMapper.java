package app.RecolectandoAPI.RecolectandoAPI.entities.sector;

import app.RecolectandoAPI.RecolectandoAPI.entities.building.Building;
import org.springframework.stereotype.Service;

@Service
public class SectorMapper {

    public Sector toSector(SectorRequest sR, Building b) {
        return Sector.builder()
                .name(sR.getName())
                .building(b)
                .build();
    }

    public SectorResponse toSectorResponse (Sector s) {
        return SectorResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .building_id(s.getBuilding().getId())
                .build();
    }

    public void updateSectorFromSectorRequest(SectorRequest sR, Sector s) {
        s.setName(sR.getName());
    }
}
