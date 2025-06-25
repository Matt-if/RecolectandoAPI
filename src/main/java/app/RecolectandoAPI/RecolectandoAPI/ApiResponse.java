package app.RecolectandoAPI.RecolectandoAPI;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    public String msg;
    public DTO data;
}
