package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.util.Constants;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.Constants.FORMATTER;

@Service
public class StatsClient extends BaseClient {

    private final Constants encoder;

    public StatsClient(@Value("${stats-service.url}") String serverUrl,
                       RestTemplateBuilder builder,
                       Constants encoder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
        this.encoder = encoder;
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String encodedStart = URLEncoder.encode(start.format(FORMATTER), StandardCharsets.UTF_8);
        String encodedEnd = URLEncoder.encode(end.format(FORMATTER), StandardCharsets.UTF_8);
        Map<String, Object> parameters = Map.of(
                "start", encodedStart,
                "end", encodedEnd,
                "uris", uris,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> save(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }
}
