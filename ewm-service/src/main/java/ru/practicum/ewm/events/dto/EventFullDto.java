package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.users.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Getter
@Builder
public final class EventFullDto {

    private final Long id;

    @NotBlank
    private final String annotation;

    @NotNull
    private final CategoryDto category;

    private final Long confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime createdOn;

    private final String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime eventDate;

    @NotNull
    private final UserShortDto initiator;

    @NotNull
    private final LocationDto location;

    @NotNull
    private final boolean paid;

    private final Integer participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime publishedOn;

    private final boolean requestModeration;

    private final EventState state;

    @NotBlank
    private final String title;

    private final Long views;

    @JsonCreator
    public EventFullDto(@JsonProperty("id") Long id,
                        @JsonProperty("annotation") String annotation,
                        @JsonProperty("category") CategoryDto category,
                        @JsonProperty("confirmedRequests") Long confirmedRequests,
                        @JsonProperty("createdOn") LocalDateTime createdOn,
                        @JsonProperty("description") String description,
                        @JsonProperty("eventDate") LocalDateTime eventDate,
                        @JsonProperty("initiator") UserShortDto initiator,
                        @JsonProperty("location") LocationDto location,
                        @JsonProperty("paid") Boolean paid,
                        @JsonProperty("participantLimit") Integer participantLimit,
                        @JsonProperty("publishedOn") LocalDateTime publishedOn,
                        @JsonProperty("requestModeration") boolean requestModeration,
                        @JsonProperty("state") EventState state,
                        @JsonProperty("title") String title,
                        @JsonProperty("views") Long views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.views = views;
    }
}
