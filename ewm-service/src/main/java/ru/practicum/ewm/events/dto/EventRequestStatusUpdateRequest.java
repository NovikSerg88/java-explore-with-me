package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.requests.dto.Status;

import java.util.List;

@Getter
@Builder
public final class EventRequestStatusUpdateRequest {
    private final List<Long> requestIds;
    private final Status status;

    @JsonCreator
    public EventRequestStatusUpdateRequest(@JsonProperty("requestIds") List<Long> requestIds,
                                           @JsonProperty("status") Status status) {
        this.requestIds = requestIds;
        this.status = status;
    }
}
