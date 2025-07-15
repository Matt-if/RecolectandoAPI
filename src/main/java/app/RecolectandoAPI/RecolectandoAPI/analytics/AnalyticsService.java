package app.RecolectandoAPI.RecolectandoAPI.analytics;

import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final PersonalizedQueryRepo personalizedQueryRepo;
    private final JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> runPersonalizedQuery(Long queryId, Object... params) {
        PersonalizedQuery pq = personalizedQueryRepo.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));

        // NOTE: For real systems, make sure the query is safe to execute!
        return jdbcTemplate.queryForList(pq.getQueryText(), params); // the format returned is like `List<Map<String, Object>>`.
    }

    public List<Map<String,Object>> kgOfWasteTypeBySectorYearMonth(RetrievalType type, Integer year, Integer month) {
        return runPersonalizedQuery(2L, type.ordinal(), year, year, month, month);
        //if type is sent, toString() is applied automatically to the enum,
        // but we need the ordinal value, which is the number saved on the DB.
    }
}
