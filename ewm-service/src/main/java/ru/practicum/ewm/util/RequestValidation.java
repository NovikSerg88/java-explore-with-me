package ru.practicum.ewm.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.RequestValidationException;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventsRepository;
import ru.practicum.ewm.events.service.StatsService;
import ru.practicum.ewm.requests.model.ParticipationRequest;
import ru.practicum.ewm.requests.repository.RequestRepository;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class RequestValidation {

    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final RequestRepository requestRepository;
    private final StatsService statsService;

    public User userValidate(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID = %s not found", userId)));
    }

    public Event eventValidate(Long eventId) {
        return eventsRepository.findById(eventId)
                .orElseThrow(() -> new RequestValidationException(String.format("Event with ID = %s not found", eventId)));
    }

    public void userNotInitiatorValidate(Long userId, Event event) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new RequestValidationException(String.format("Event initiator with ID = %s can not " +
                    "request the event: %s", userId, event));
        }
    }

    public void eventHasAlreadyPublishedValidate(Event event) {
        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new RequestValidationException(String.format("Event: %s has already published", event));
        }
    }

    public void participantLimitValidate(Event event) {
        Long confirmedRequests = statsService.getConfirmedCountForEvent(event.getId());
        if (confirmedRequests >= event.getParticipantLimit() && !event.getRequestModeration()) {
            throw new RequestValidationException(String.format("Participants limit in event: %s reached", event));
        }
    }

    public void repeatedRequestValidate(Long userId, Long eventId) {
        ParticipationRequest request = requestRepository.findByRequesterIdAndEventId(userId, eventId);
        if (request != null) {
            throw new RequestValidationException(String.format("There is already a request from this user by the id=%d to " +
                    "the event by the ID = %s", userId, eventId));
        }
    }
}

