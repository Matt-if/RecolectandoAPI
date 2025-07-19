package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.RetrievalDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorRepo;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.SectorNotFoundException;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RetrievalService {
    private final RetrievalRepo retrievalRepo;
    private final RetrievalMapper retrievalMapper;
    private final UserRepo userRepo;
    private final SectorRepo sectorRepo;

    //Debido al dominio seria extremadamente raro que un retrieval requiera editarse, por ahora no se tendra en cuenta.
    // Por eso no hay control del ID.
    public Retrieval saveRetrieval(RetrievalRequest retrievalRequest) {

        User user = userRepo.findById(retrievalRequest.getUser_id()).orElseThrow(UserNotFoundException::new);

        Sector sector = sectorRepo.findById(retrievalRequest.getSector_id()).orElseThrow(SectorNotFoundException::new);

        return retrievalRepo.save(retrievalMapper.toRetrieval(retrievalRequest, user, sector));
    }

    public List<Retrieval> listAll() {
        try {
            return retrievalRepo.findAllByDeleted(false); //antes usaba findAll() y despues filtraba en el controller
        }
         catch (Exception e) {
            throw new RuntimeException("Error al listar recolecciones: " + e.getMessage());
        }
    }
}
