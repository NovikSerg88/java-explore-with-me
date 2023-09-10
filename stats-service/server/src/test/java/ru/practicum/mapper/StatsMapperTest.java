package ru.practicum.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

public class StatsMapperTest {

    private final StatsMapper statsMapper = new StatsMapper();

    @Test
    void mapToEntityTest() {
        String app = "ewm-main-service";
        String uri = "/events/1";
        String ip = "/ip";
        LocalDateTime timestamp = LocalDateTime.parse("2020-05-05 00:00:00",
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));


        EndpointHit expectedHit = EndpointHit.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(timestamp)
                .build();
        EndpointHitDto requestHit = EndpointHitDto.builder()
                .app(app)
                .ip(ip)
                .uri(uri)
                .timestamp("2020-05-05 00:00:00")
                .build();
        EndpointHit actualHit = statsMapper.mapToEntity(requestHit);
        assertThat(actualHit.getApp()).isEqualTo(expectedHit.getApp());
        assertThat(actualHit.getIp()).isEqualTo(expectedHit.getIp());
        assertThat(actualHit.getUri()).isEqualTo(expectedHit.getUri());
        assertThat(actualHit.getTimestamp()).isEqualTo(expectedHit.getTimestamp());
    }
}
