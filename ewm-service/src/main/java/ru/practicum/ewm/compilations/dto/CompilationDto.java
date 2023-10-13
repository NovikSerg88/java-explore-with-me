package ru.practicum.ewm.compilations.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.events.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public final class CompilationDto {
    private final Long id;
    @NotNull
    private final Boolean pinned;
    @NotBlank
    @NotNull
    private final String title;
    private final List<EventShortDto> events;

    @JsonCreator
    public CompilationDto(@JsonProperty("id") Long id,
                          @JsonProperty("pinned") Boolean pinned,
                          @JsonProperty("title") String title,
                          @JsonProperty("events") List<EventShortDto> events) {
        this.id = id;
        this.pinned = pinned;
        this.title = title;
        this.events = events;
    }
}
