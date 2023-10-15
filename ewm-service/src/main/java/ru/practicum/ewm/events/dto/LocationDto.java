package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class LocationDto {
    private final float lat;
    private final float lon;

    @JsonCreator
    public LocationDto(@JsonProperty("lat") float lat,
                       @JsonProperty("lon") float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
