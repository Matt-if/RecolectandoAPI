package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuildingRepo extends JpaRepository<Building, Long> {

    boolean existsByName(String name);

    Building findByName(String name);

    List<Building> findAllByDeleted(boolean deleted);
}
