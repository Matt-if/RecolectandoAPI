package app.RecolectandoAPI.RecolectandoAPI.analytics;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.RetrievalType;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.exceptions.TypeOfRetrievalNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final PersonalizedQueryRepo personalizedQueryRepo;
    private final JdbcTemplate jdbcTemplate;
    private final AnalyticsMapper analyticsMapper;

    public List<Map<String,Object>> runPersonalizedQuery(Long queryId, Object... params) {
        PersonalizedQuery pq = personalizedQueryRepo.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));

        return jdbcTemplate.queryForList(pq.getQueryText(), params); // format returned is like `List<Map<String, Object>>`.
    }

    public List<DTO> kgOfWasteTypeBySectorYearMonth(String type, Integer year, Integer month) {

        if (!(Arrays.toString(RetrievalType.values())).contains(type)) throw new TypeOfRetrievalNotExistsException(type);

        return runPersonalizedQuery(2L, RetrievalType.valueOf(type), year, year, month, month).stream()
                .map(analyticsMapper::toAnalyticsResponse).collect(Collectors.toList());
        //if type is sent, toString() is applied automatically to the enum,
        // but we need the ordinal value, which is the number saved on the DB.
    }
}
