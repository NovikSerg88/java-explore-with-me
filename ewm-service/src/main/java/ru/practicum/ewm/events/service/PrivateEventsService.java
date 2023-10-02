package ru.practicum.ewm.events.service;

import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventsService {

    EventFullDto createEvent(NewEventDto dto, Long userId);

    List<EventShortDto> getEventsOfUser(Long userId, int from, int size);

    EventFullDto getEventOfUser(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest dto);

    List<ParticipationRequestDto> getRequestsOfUserEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId,
                                                       Long eventId,
                                                       EventRequestStatusUpdateRequest dto);
}
