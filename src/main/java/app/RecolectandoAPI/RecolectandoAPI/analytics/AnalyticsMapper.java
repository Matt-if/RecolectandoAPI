package app.RecolectandoAPI.RecolectandoAPI.analytics;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AnalyticsMapper {

    public AnalyticsResponse toAnalyticsResponse (Map<String,Object> map) {
        return AnalyticsResponse.builder()
                .row(map)
                .build();
    }
}
