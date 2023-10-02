package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.Constants.FORMATTER;

@Component
public class StatsClient extends BaseClient {

    public StatsClient(@Value("${stats-service.url}") String serverUrl,
                       RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String startEncoded = URLEncoder.encode(start.format(FORMATTER), StandardCharsets.UTF_8);
        String endEncoded = URLEncoder.encode(end.format(FORMATTER), StandardCharsets.UTF_8);
        Map<String, Object> params = new HashMap<>();
        params.put("start", startEncoded);
        params.put("end", endEncoded);
        StringBuilder sb = new StringBuilder();
        sb.append("/stats")
                .append("?")
                .append("start={start}")
                .append("&")
                .append("end={end}");
        if (uris != null && !uris.isEmpty()) {
            String uriString = String.join(",", uris);
            params.put("uris", uriString);
            sb.append("&")
                    .append("uris={uris}");
        }
        if (unique != null) {
            params.put("unique", unique);
            sb.append("&")
                    .append("unique={unique}");
        }
        return get(sb.toString(), params);
    }

    public ResponseEntity<Object> save(String app, String uri, String ip, LocalDateTime hitTimeStamp) {
        EndpointHitDto hit = EndpointHitDto.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(hitTimeStamp)
                .build();
        return post("/hit", hit);
    }
}
