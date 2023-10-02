package ru.practicum.ewm.requests.service;

import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestsService {

    List<ParticipationRequestDto> getRequests(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
