package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.RetrievalDTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.sector.SectorRepo;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RetrievalService {
    private final RetrievalRepo retrievalRepo;
    private final UserRepo userRepo;
    private final SectorRepo sectorRepo;

    public Retrieval create(RetrievalDTO retrievalDto) {

        User user = userRepo.findById(retrievalDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sector sector = sectorRepo.findById(retrievalDto.getSector_id())
                .orElseThrow(() -> new RuntimeException("Sector not found"));

        Retrieval retrieval = Retrieval.builder()
                .weight(retrievalDto.getWeight())
                .volume(retrievalDto.getVolume())
                .type(retrievalDto.getType())
                .date(retrievalDto.getDate())
                .time(retrievalDto.getTime())
                .observations(retrievalDto.getObservations())
                .user(user)
                .sector(sector)
                .build();

        return retrievalRepo.save(retrieval);
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
