package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalDTO {
    private Long id;
    private double weight;
    private double volume;
    private String observations;
    private RetrievalType type;
    private LocalDate date;
    private LocalTime time;
    private long user_id;
    private long sector_id;
}
