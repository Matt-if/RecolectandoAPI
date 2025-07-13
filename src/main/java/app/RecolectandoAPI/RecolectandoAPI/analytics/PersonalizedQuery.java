package app.RecolectandoAPI.RecolectandoAPI.analytics;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "queries")
public class PersonalizedQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Lob // To allow long queries
    @Column(length = 256, nullable = false)
    private String queryText;

    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
