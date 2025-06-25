package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDTO implements DTO{
    private Long id;
    private String name;
    private String address;
    private boolean deleted;
    private String sectors;
}
