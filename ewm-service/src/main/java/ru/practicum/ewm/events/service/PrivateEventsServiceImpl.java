package ru.practicum.ewm.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoriesRepository;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.RequestValidationException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.mapper.EventMapper;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.model.Location;
import ru.practicum.ewm.events.repository.EventsRepository;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.dto.Status;
import ru.practicum.ewm.requests.mapper.RequestMapper;
import ru.practicum.ewm.requests.model.ParticipationRequest;
import ru.practicum.ewm.requests.repository.RequestRepository;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.repository.UserRepository;
import ru.practicum.ewm.util.DateValidation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PrivateEventsServiceImpl implements PrivateEventsService {

    private final EventsRepository eventsRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final EventMapper eventMapper;
    private final DateValidation dateValidation;


    @Override
    public EventFullDto createEvent(NewEventDto dto, Long userId) {
        dateValidation.checkEventDate(dto.getEventDate());
        User initiator = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                String.format("User with ID={} not found.", userId)));
        Category category = categoriesRepository.findById(dto.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + dto.getCategory()));
        Event event = eventMapper.mapToEvent(dto, category, initiator);
        return eventMapper.mapToEventFullDto(eventsRepository.save(event));
    }

    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, int from, int size) {
        Pageable page = PageRequest.of(from / size, size);
        Page<Event> eventPage = eventsRepository.findAllByInitiatorId(userId, page);
        return eventPage.stream()
                .map(eventMapper::mapToEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventOfUser(Long userId, Long eventId) {
        checkUserExists(userId);
        Event event = eventsRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with ID={} of user with ID={} not found", eventId, userId)));
        return eventMapper.mapToEventFullDto(event);
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest update) {
        checkUserExists(userId);
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with ID={} not found", eventId)));
        validateNewEventState(event, update);
        Event updated = updateEvent(event, update);
        validateNewEventDate(updated);
        return eventMapper.mapToEventFullDto(eventsRepository.saveAndFlush(updated));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsOfUserEvent(Long userId, Long eventId) {
        checkUserExists(userId);
        eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with ID={} not found", eventId)));
        List<ParticipationRequest> requests = requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId);
        return requests.stream()
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId,
                                                              Long eventId,
                                                              EventRequestStatusUpdateRequest dto) {
        checkUserExists(userId);
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with ID={} not found", eventId)));
        EventFullDto eventFullDto = eventMapper.mapToEventFullDto(event);
        checkLimit(event, dto);
        List<ParticipationRequest> requests = requestRepository.findAllByIdIn(dto.getRequestIds());

        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        for (ParticipationRequest request : requests) {
            checkRequestStatus(request);
            if (eventFullDto.getConfirmedRequests() + 1 > event.getParticipantLimit()) {
                request.setStatus(Status.CANCELED);
                requestRepository.save(request);
                rejectedRequests.add(requestMapper.mapToDto(request));
            }
            if (dto.getStatus().equals(Status.CONFIRMED)) {
                request.setStatus(Status.CONFIRMED);
                requestRepository.save(request);
                confirmedRequests.add(requestMapper.mapToDto(request));
            } else {
                request.setStatus(Status.REJECTED);
                requestRepository.save(request);
                rejectedRequests.add(requestMapper.mapToDto(request));
            }
        }

        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }


    private User checkUserExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                String.format("User with ID={} not found.", userId)));
    }

    private void validateNewEventState(Event event, UpdateEventUserRequest update) {
        if (EventState.PUBLISHED.equals(event.getState())) {
            throw new RequestValidationException("Only events with the state PENDING or CANCELED are available for update.");
        }
    }

    private void validateNewEventDate(Event event) {
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Date and time of new Event should be at least two hours from now.");
        }
    }

    private Event updateEvent(Event event, UpdateEventUserRequest update) {
        if (update.getAnnotation() != null) {
            event.setAnnotation(update.getAnnotation());
        }
        if (update.getCategory() != null) {
            Category category = categoriesRepository.findById(update.getCategory())
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Category with ID={} not found.", update.getCategory())));
            event.setCategory(category);
        }
        if (update.getDescription() != null) {
            event.setDescription(update.getDescription());
        }
        if (update.getEventDate() != null) {
            event.setEventDate(update.getEventDate());
        }
        if (update.getLocation() != null) {
            event.setLocation(Location.builder()
                    .lon(update.getLocation().getLon())
                    .lat(update.getLocation().getLat())
                    .build());
        }
        if (update.getPaid() != null) {
            event.setPaid(update.getPaid());
        }
        if (update.getParticipantLimit() != null) {
            event.setParticipantLimit(update.getParticipantLimit());
        }
        if (update.getRequestModeration() != null) {
            event.setRequestModeration(update.getRequestModeration());
        }
        if (update.getTitle() != null) {
            event.setTitle(update.getTitle());
        }
        if (update.getStateAction() != null) {
            switch (update.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                default:
                    throw new RuntimeException("Unknown EventStateAction " + update.getStateAction());
            }
        }
        return event;
    }

    private void checkLimit(Event event, EventRequestStatusUpdateRequest dto) {
        EventFullDto eventFullDto = eventMapper.mapToEventFullDto(event);
        Long confirmedRequests = eventFullDto.getConfirmedRequests();
        Long limit = eventFullDto.getParticipantLimit() - confirmedRequests;
        Status status = Status.valueOf(String.valueOf(dto.getStatus()));
        if (status.equals(Status.CONFIRMED) && limit <= 0) {
            throw new RequestValidationException("The limit of requests to participate in the event has been reached");
        }
    }

    private void checkRequestStatus(ParticipationRequest request) {
        if (!request.getStatus().equals(Status.PENDING)) {
            throw new RequestValidationException("The request should have status PENDING for request confirmation");
        }
    }
}
