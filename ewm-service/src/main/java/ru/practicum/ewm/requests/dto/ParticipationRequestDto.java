package ru.practicum.ewm.requests.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Getter
@Builder
public final class ParticipationRequestDto {
    private final Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime created;
    private final Long event;
    private final Long requester;
    private final Status status;

    @JsonCreator
    public ParticipationRequestDto(@JsonProperty("id") Long id,
                                   @JsonProperty("created") LocalDateTime created,
                                   @JsonProperty("event") Long event,
                                   @JsonProperty("requester") Long requester,
                                   @JsonProperty("status") Status status) {
        this.id = id;
        this.created = created;
        this.event = event;
        this.requester = requester;
        this.status = status;
    }
}
