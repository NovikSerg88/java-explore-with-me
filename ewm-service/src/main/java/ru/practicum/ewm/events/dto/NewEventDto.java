package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Getter
@Builder
public final class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private final String annotation;

    @NotNull
    private final Long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private final String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime eventDate;

    @NotNull
    private final LocationDto location;

    private final boolean paid;

    private final int participantLimit;

    private final Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120)
    private final String title;

    @JsonCreator
    public NewEventDto(@JsonProperty("annotation") String annotation,
                       @JsonProperty("category") Long category,
                       @JsonProperty("description") String description,
                       @JsonProperty("eventDate") LocalDateTime eventDate,
                       @JsonProperty("location") LocationDto location,
                       @JsonProperty("paid") boolean paid,
                       @JsonProperty("participantLimit") int participantLimit,
                       @JsonProperty("requestModeration") Boolean requestModeration,
                       @JsonProperty("title") String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        requestModeration = null == requestModeration || requestModeration.equals(true);
        this.requestModeration = requestModeration;
        this.title = title;
    }
}
