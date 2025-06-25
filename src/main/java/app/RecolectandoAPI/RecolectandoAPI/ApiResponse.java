package app.RecolectandoAPI.RecolectandoAPI;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    public String msg;
    public List <DTO> data;
}
