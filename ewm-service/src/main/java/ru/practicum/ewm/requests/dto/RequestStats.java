package ru.practicum.ewm.requests.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class RequestStats {
    private final Long eventId;
    private final Long confirmedRequests;

    @JsonCreator
    public RequestStats(@JsonProperty("eventId") Long eventId,
                        @JsonProperty("confirmedRequests") Long confirmedRequests) {
        this.eventId = eventId;
        this.confirmedRequests = confirmedRequests;
    }
}
