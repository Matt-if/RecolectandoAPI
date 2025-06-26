package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingSummaryDTO implements DTO{
    private Long id;
    private String name;
}
