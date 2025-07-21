package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse implements DTO {
    private Long id;
    private String name;
    private String address;
    private List<String> sectors_names;
}
