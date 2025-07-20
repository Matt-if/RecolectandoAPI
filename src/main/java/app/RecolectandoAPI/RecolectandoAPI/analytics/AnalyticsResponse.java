package app.RecolectandoAPI.RecolectandoAPI.analytics;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsResponse implements DTO {
    private Map<String, Object> row = new HashMap<>();
}
