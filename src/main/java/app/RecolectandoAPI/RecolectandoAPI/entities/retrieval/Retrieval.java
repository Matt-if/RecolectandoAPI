package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
public class Retrieval {
    @Id
    private Long id;

    private double volume;

    private double weight;

    private String observations;

    @Column(nullable = false)
    private Date date; // for dd-MM-yyyy

    @Column(nullable = false)
    private LocalTime time; // for hh:min

    @ManyToOne
    private User user; //user who created the collection

    @ManyToOne
    private Sector sector;
}
