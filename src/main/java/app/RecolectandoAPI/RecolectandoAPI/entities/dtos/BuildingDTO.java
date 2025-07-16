package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDTO implements DTO{
    private Long id;
    @NotBlank(message = "El nombre no puede ser vacio") @NotNull(message = "El nombre es requerido")
    private String name;
    private String address;
    private boolean deleted;
    private List<SectorDTO> sectors; // antes -> String sectors;
}
