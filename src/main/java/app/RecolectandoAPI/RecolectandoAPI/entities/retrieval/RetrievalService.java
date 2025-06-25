package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RetrievalService {
    private final RetrievalRepo retrievalRepo;
}
