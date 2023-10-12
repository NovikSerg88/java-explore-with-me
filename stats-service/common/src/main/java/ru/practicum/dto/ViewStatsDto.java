package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class ViewStatsDto {
    private final String app;
    private final String uri;
    private final Long hits;

    @JsonCreator
    public ViewStatsDto(@JsonProperty("app") String app,
                        @JsonProperty("uri") String uri,
                        @JsonProperty("hits") Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
