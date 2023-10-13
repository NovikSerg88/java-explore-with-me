package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

@Component
public class StatsMapper {

    public EndpointHit mapToEntity(EndpointHitDto dto) {
        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
