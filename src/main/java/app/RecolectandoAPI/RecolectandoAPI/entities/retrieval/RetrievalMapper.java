package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import org.springframework.stereotype.Service;

@Service
public class RetrievalMapper {
    public Retrieval toRetrieval (RetrievalRequest rQ, User u, Sector s) {
        return Retrieval.builder()
                .type(rQ.getType())
                .time(rQ.getTime())
                .date(rQ.getDate())
                .observations(rQ.getObservations())
                .weight(rQ.getWeight())
                .volume(rQ.getVolume())
                .user(u)
                .sector(s)
                .build();
    }

    public RetrievalResponse toRetrievalResponse (Retrieval r) {
        return RetrievalResponse.builder()
                .sector_name(r.getSector().getName())
                .user_username(r.getUser().getUsername())
                .type(r.getType())
                .time(r.getTime())
                .date(r.getDate())
                .weight(r.getWeight())
                .volume(r.getVolume())
                .observations(r.getObservations())
                .build();
    }
}
