package app.RecolectandoAPI.RecolectandoAPI.entities.retrieval;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RetrievalRepo extends JpaRepository<Retrieval, Long> {

    List<Retrieval> findAllByDeleted(boolean deleted);
}
