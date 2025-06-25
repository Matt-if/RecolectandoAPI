package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retrievals")
@RequiredArgsConstructor
public class RetrievalController {
    private final RetrievalService retrievalService;


}
