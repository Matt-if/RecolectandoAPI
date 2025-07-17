package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import app.RecolectandoAPI.RecolectandoAPI.analytics.AnalyticsResultDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorSummaryDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;

import java.util.Map;

public class ToDTO {

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
                .date(retrieval.getDate())
                .time(retrieval.getTime())
                .user_id(retrieval.getUser().getId())
                .sector_id(retrieval.getSector().getId())
                .build();
    }

    public static RetrievalTypeDTO retrievalType(RetrievalType r) {
        return RetrievalTypeDTO.builder()
                .type(r.name())
                .build();
    }

    public static UserDTO user(User user){
        return null;
    }

    public static AnalyticsResultDTO analyticsResult (Map<String,Object> map) {
        return AnalyticsResultDTO.builder()
                .row(map)
                .build();
    }


}
