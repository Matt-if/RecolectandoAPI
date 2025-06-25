package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import app.RecolectandoAPI.RecolectandoAPI.entities.building.Building;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;

public class ToDTO {
    public static BuildingDTO building(Building building){
        return BuildingDTO.builder()
                .id(building.getId())
                .name(building.getName())
                .address(building.getAddress())
                .sectors(building.getSectors_String())
                .build();
    }

    public static RetrievalDTO retrieval(Retrieval retrieval){
        return null;
    }

    public static UserDTO user(User user){
        return null;
    }

    public static SectorDTO sector (Sector s) {
        return SectorDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .building_id(s.getBuilding().getId())
                .retrievals(s.getRetrievals_String())
                .build();
    }
}
