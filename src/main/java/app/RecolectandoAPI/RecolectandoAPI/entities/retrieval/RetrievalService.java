package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorRepo;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.SectorNotFoundException;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RetrievalService {
    private final RetrievalRepo retrievalRepo;
    private final RetrievalMapper retrievalMapper;
    private final UserRepo userRepo;
    private final SectorRepo sectorRepo;
    private final RetrievalTypeMapper retTypeMapper;

    //Debido al dominio seria extremadamente raro que un retrieval requiera editarse, por ahora no se tendra en cuenta.
    // Por eso no hay control del ID.
    public Retrieval saveRetrieval(RetrievalRequest retrievalRequest) {

        User user = userRepo.findById(retrievalRequest.getUser_id()).orElseThrow(() -> new UserNotFoundException(retrievalRequest.getUser_id()));

        Sector sector = sectorRepo.findById(retrievalRequest.getSector_id()).orElseThrow(() -> new SectorNotFoundException(retrievalRequest.getSector_id()));

        return retrievalRepo.save(retrievalMapper.toRetrieval(retrievalRequest, user, sector));
    }

    public List<DTO> listAll() {
        return retrievalRepo.findAllByDeleted(false).stream()
                .map(retrievalMapper::toRetrievalResponse)
                .collect(Collectors.toList());
    }

    public List<DTO> listAllRetrievalTypes() {
        return Arrays.stream(RetrievalType.values())
                .map(retTypeMapper::toRetrievalTypeResponse)
                .collect(Collectors.toList());
    }
}
