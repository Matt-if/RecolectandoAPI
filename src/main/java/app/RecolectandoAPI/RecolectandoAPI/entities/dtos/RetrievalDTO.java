package app.RecolectandoAPI.RecolectandoAPI.entities.dtos;

import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String date;
    private String time;
    private long user_id;
    private long sector_id;
}
