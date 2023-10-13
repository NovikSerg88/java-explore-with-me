package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@Builder
public final class EventShortDto {
    private final Long id;
    private final String annotation;
    private final CategoryDto category;
    private final Long confirmedRequests;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private final LocalDateTime eventDate;
    private final UserShortDto initiator;
    @NotNull
    private final Boolean paid;
    private final String title;
    private final Long views;

    @JsonCreator
    public EventShortDto(@JsonProperty("id") Long id,
                         @JsonProperty("annotation") String annotation,
                         @JsonProperty("category") CategoryDto category,
                         @JsonProperty("confirmedRequests") Long confirmedRequests,
                         @JsonProperty("eventDate") LocalDateTime eventDate,
                         @JsonProperty("initiator") UserShortDto initiator,
                         @JsonProperty("paid") Boolean paid,
                         @JsonProperty("title") String title,
                         @JsonProperty("views") Long views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
        this.views = views;
    }
}
