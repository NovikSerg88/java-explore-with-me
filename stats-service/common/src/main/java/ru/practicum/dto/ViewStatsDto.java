package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public final class ViewStatsDto {
    private final String app;
    private final String uri;
    private final Long hits;
}
