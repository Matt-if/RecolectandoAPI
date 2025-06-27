package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "retrievals")
@Builder
@AllArgsConstructor
public class Retrieval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double volume;

    private double weight;

    private String observations;

    @Column(nullable = false)
    private RetrievalType type;

    @Column(nullable = false)
    private LocalDate date; // for dd-MM-yyyy

    @Column(nullable = false)
    private LocalTime time; // for hh:min

    @ManyToOne
    private User user; //user who created the collection

    @ManyToOne
    private Sector sector; //sector where the retrieval was made
}
