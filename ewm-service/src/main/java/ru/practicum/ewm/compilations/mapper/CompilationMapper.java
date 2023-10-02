package ru.practicum.ewm.compilations.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.events.mapper.EventMapper;
import ru.practicum.ewm.events.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public CompilationDto mapToDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents().stream()
                        .map(eventMapper::mapToEventShortDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Compilation mapToEntity(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(dto.isPinned())
                .title(dto.getTitle())
                .build();
    }
}
