package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Component
public class StatsMapper {

    public EndpointHit mapToEntity(EndpointHitDto dto) {
        LocalDateTime timestamp = LocalDateTime.parse(dto.getTimestamp(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(timestamp)
                .build();
    }
}
