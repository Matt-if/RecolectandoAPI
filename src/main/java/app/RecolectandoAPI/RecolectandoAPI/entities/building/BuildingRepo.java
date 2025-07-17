package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BuildingRepo extends JpaRepository<Building, Long> {

    boolean existsByName(String name);

    Building findByName(String name);

    Optional<Building> findByIdAndDeleted(Long id, boolean deleted);

    List<Building> findAllByDeleted(boolean deleted);
}
