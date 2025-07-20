package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import app.RecolectandoAPI.RecolectandoAPI.analytics.AnalyticsResponse;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;

import java.util.Map;

public class ToDTO {

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





}
