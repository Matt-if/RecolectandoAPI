package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import org.springframework.stereotype.Service;

@Service
public class RetrievalTypeMapper {
    public RetrievalTypeResponse toRetrievalTypeResponse(RetrievalType r) {
        return RetrievalTypeResponse.builder()
                .type(r.name())
                .build();
    }
}
