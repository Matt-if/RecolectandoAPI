package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO implements DTO{
    private long id;
    private String name;
    private boolean deleted;
    private long building_id;
    private String retrievals;
}
