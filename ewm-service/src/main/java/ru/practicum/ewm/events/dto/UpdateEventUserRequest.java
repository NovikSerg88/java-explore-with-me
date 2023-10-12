package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@Builder
public final class UpdateEventUserRequest {
    @Size(min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    @Size(min = 20, max = 7000)
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime eventDate;
    private final LocationDto location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final StateAction stateAction;
    @Size(min = 3, max = 120)
    private final String title;

    @JsonCreator
    public UpdateEventUserRequest(@JsonProperty("annotation") String annotation,
                                  @JsonProperty("category") Long category,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("eventDate") LocalDateTime eventDate,
                                  @JsonProperty("location") LocationDto location,
                                  @JsonProperty("paid") Boolean paid,
                                  @JsonProperty("participantLimit") Integer participantLimit,
                                  @JsonProperty("requestModeration") Boolean requestModeration,
                                  @JsonProperty("stateAction") StateAction stateAction,
                                  @JsonProperty("title") String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.stateAction = stateAction;
        this.title = title;
    }
}
