package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalRequest implements DTO {

    @NotNull(message = "debe ser definido")
    @Positive(message = "debe ser mayor a 0")
    private Double weight;

    private double volume;
    private String observations;

    @NotNull(message = "Tipo de residuo debe seleccionarse")
    private RetrievalType type;

    @NotNull(message = "La fecha es obligatoria. Respetar formato yyyy-MM-dd")
    @PastOrPresent(message = "no puede ser futura")
    private LocalDate date;

    @NotNull(message = "La hora es obligatoria. Respetar formato HH:mm")
    private LocalTime time;

    @NotNull(message = "debe ser provisto")
    private Long user_id;
    @NotNull(message = "debe ser provisto")
    private Long sector_id;
}
