package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.DTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalResponse implements DTO {
    private double weight;
    private double volume;
    private String observations;
    private RetrievalType type;
    private LocalDate date;
    private LocalTime time;
    private String user_username;
    private String sector_name;
}
