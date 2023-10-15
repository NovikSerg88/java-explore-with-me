package ru.practicum.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

@Getter
@Builder
public final class EventRequestStatusUpdateResult {
    private final List<ParticipationRequestDto> confirmedRequests;
    private final List<ParticipationRequestDto> rejectedRequests;

    @JsonCreator
    public EventRequestStatusUpdateResult(@JsonProperty("confirmedRequests") List<ParticipationRequestDto> confirmedRequests,
                                          @JsonProperty("rejectedRequests") List<ParticipationRequestDto> rejectedRequests) {
        this.confirmedRequests = confirmedRequests;
        this.rejectedRequests = rejectedRequests;
    }
}
