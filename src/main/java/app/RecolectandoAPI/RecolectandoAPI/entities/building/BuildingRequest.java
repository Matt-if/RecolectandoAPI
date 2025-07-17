package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {
    private Long id;

    @NotBlank(message = "El nombre no puede ser vacio")
    private String name;

    private String address;
}

