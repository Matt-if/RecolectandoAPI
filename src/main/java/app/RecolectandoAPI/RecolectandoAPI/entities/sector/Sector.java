package app.RecolectandoAPI.RecolectandoAPI.entities.sector;

import app.RecolectandoAPI.RecolectandoAPI.entities.building.Building;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "sectors")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Antes name = unique porque me parecio A MI, pero salvo que el admin exprese lo contrario,
    // pueden repetirse en edifcios DISTINTOS: ejemplo tener "Sector A" en edificio 1 y "Sector A" en edificio 2
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Building building;

    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "sector")
    private List<Retrieval> retrievals;

    public String getRetrievals_String() {
        return (retrievals == null) ? "" : retrievals.toString();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
