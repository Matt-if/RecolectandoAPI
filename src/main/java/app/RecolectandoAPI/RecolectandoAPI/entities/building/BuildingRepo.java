package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepo extends JpaRepository<Building, Long> {
    Building findByName(String name);

    boolean existsByName(String name);
}
