package ru.practicum.ewm.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.dto.Status;
import ru.practicum.ewm.requests.mapper.RequestMapper;
import ru.practicum.ewm.requests.model.ParticipationRequest;
import ru.practicum.ewm.requests.repository.RequestRepository;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.util.RequestValidation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateRequestsServiceImpl implements PrivateRequestsService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final RequestValidation requestValidation;

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        requestValidation.userValidate(userId);
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);
        return requests.stream()
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User requester = requestValidation.userValidate(userId);
        Event event = requestValidation.eventValidate(eventId);
        requestValidation.eventHasAlreadyPublishedValidate(event);
        requestValidation.userNotInitiatorValidate(userId, event);
        requestValidation.participantLimitValidate(event);
        requestValidation.repeatedRequestValidate(userId, eventId);
        ParticipationRequest request = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(requester)
                .status(Status.PENDING)
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
        }
        return requestMapper.mapToDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        requestValidation.userValidate(userId);
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with ID = %s not found", requestId)));
        request.setStatus(Status.CANCELED);
        request = requestRepository.save(request);
        return requestMapper.mapToDto(request);
    }
}
