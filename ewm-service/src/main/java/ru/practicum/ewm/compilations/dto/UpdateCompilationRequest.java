package ru.practicum.ewm.compilations.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public final class UpdateCompilationRequest {
    private final List<Long> events = new ArrayList<>();
    private final Boolean pinned;
    @Size(min = 1, max = 50)
    private final String title;

    @JsonCreator
    public UpdateCompilationRequest(@JsonProperty("pinned") Boolean pinned,
                                    @JsonProperty("title") String title) {
        this.pinned = pinned;
        this.title = title;
    }
}
