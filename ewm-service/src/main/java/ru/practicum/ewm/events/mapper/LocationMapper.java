package ru.practicum.ewm.events.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.events.dto.LocationDto;
import ru.practicum.ewm.events.model.Location;

@Component
@RequiredArgsConstructor
public class LocationMapper {

    public LocationDto mapToDto(Location entity) {
        return LocationDto.builder()
                .lat(entity.getLat())
                .lon(entity.getLon())
                .build();
    }

    public Location mapToLocation(LocationDto dto) {
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }
}
