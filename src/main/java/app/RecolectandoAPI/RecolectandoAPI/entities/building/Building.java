package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Building {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private boolean deleted = false;

    @OneToMany(mappedBy = "building")
    private List<Sector> sectors;
}
