package app.RecolectandoAPI.RecolectandoAPI.entities.sector;

import app.RecolectandoAPI.RecolectandoAPI.entities.building.Building;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "sectors")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted;

    @ManyToOne
    private Building building;

    @OneToMany(mappedBy = "sector")
    private List<Retrieval> retrievals;

    public String getRetrievals_String() {
        return (retrievals == null) ? "" : retrievals.toString();
    }
}
