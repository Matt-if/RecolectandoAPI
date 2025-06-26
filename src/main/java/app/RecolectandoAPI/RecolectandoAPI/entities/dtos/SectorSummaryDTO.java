package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorSummaryDTO implements DTO{
    private long id;
    private String name;
}
