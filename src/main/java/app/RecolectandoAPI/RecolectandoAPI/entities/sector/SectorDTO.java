package app.RecolectandoAPI.RecolectandoAPI.entities.sector;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.RetrievalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO implements DTO {
    private long id;
    private String name;
    private boolean deleted;
    private long building_id;
    private List<RetrievalDTO> retrievals;
}
