package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import app.RecolectandoAPI.RecolectandoAPI.entities.building.Building;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;

public class ToDTO {
    public static BuildingDTO completeBuilding(Building building){
        return BuildingDTO.builder()
                .id(building.getId())
                .name(building.getName())
                .address(building.getAddress())
                .sectors(building.getSectors().stream().map(ToDTO::completeSector).toList())
                .build();
    }

    public static BuildingSummaryDTO summaryBuilding(Building building){
        return BuildingSummaryDTO.builder()
                .id(building.getId())
                .name(building.getName())
                .build();
    }

    public static SectorDTO completeSector(Sector s) {
        return SectorDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .building_id(s.getBuilding().getId())
                .retrievals(s.getRetrievals().stream().map(ToDTO::retrieval).toList())
                .build();
    }

    public static SectorSummaryDTO summarySector(Sector s) {
        return SectorSummaryDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .build();
    }

    public static RetrievalDTO retrieval(Retrieval retrieval){
        return RetrievalDTO.builder()
                .id(retrieval.getId())
                .weight(retrieval.getWeight())
                .volume(retrieval.getVolume())
                .observations(retrieval.getObservations())
                .type(retrieval.getType())
                .date(retrieval.getDate().toString())
                .time(retrieval.getTime().toString())
                .user_id(retrieval.getUser().getId())
                .sector_id(retrieval.getSector().getId())
                .build();
    }

    public static UserDTO user(User user){
        return null;
    }


}
