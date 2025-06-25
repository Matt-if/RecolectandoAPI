package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

    @OneToMany(mappedBy = "building")
    private List<Sector> sectors;

    public String getSectors_String() {
        return (sectors == null) ? "" : sectors.toString();
    }
}
