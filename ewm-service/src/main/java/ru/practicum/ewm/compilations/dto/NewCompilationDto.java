package ru.practicum.ewm.compilations.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
public final class NewCompilationDto {
    private final List<Long> events;
    private final Boolean pinned;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private final String title;

    @JsonCreator
    public NewCompilationDto(@JsonProperty("events") List<Long> events,
                             @JsonProperty("pinned") Boolean pinned,
                             @JsonProperty("title") String title) {
        this.events = events;
        this.pinned = pinned;
        this.title = title;
    }
}
