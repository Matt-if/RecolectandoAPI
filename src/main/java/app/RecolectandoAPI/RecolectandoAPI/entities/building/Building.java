package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String address;

    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sector> sectors = new ArrayList<>(); //inicializacion nueva

    public void addSector(Sector sector) {
        sector.setBuilding(this);
        sectors.add(sector);
    }

    public boolean isSectorAlreadyAdded(String sectorName) {
        return sectors.stream().anyMatch(sector -> sector.getName().equalsIgnoreCase(sectorName));
    }
}
