package app.RecolectandoAPI.RecolectandoAPI.entities.sector;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorRequest {
    private long id;
    @NotBlank(message = "El nombre no puede ser vacio")
    private String name;
}
