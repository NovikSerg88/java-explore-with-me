package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.events.dto.EventShortDto;
import ru.practicum.ewm.events.dto.NewEventDto;
import ru.practicum.ewm.events.dto.UpdateEventUserRequest;
import ru.practicum.ewm.events.service.PrivateEventsService;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrivateEventsController {

    private final PrivateEventsService privateEventsService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@RequestBody @Valid NewEventDto newEventDto,
                                    @PathVariable("userId") Long userId) {
        log.info("Receiving POST request to create new event: {} for user with ID = {}", newEventDto, userId);
        return privateEventsService.createEvent(newEventDto, userId);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEventsOfUser(@PathVariable("userId") Long userId,
                                               @Valid @RequestParam(value = "from", defaultValue = "0") int from,
                                               @Valid @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Received request to GET events of user with ID={}. From: {}, size: {}",
                userId, from, size);
        return privateEventsService.getEventsOfUser(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventOfUser(@PathVariable("userId") Long userId,
                                       @PathVariable("eventId") Long eventId) {
        log.info("Received request to GET event with ID={} of user with ID={}.",
                eventId, userId);
        return privateEventsService.getEventOfUser(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable("userId") Long userId,
                                    @PathVariable("eventId") Long eventId,
                                    @RequestBody @Valid UpdateEventUserRequest dto) {
        log.info("Received PATCH request to update event with ID={} of user with ID={}.",
                eventId, userId);
        return privateEventsService.updateEvent(userId, eventId, dto);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsOfUserEvent(@PathVariable("userId") Long userId,
                                                                @PathVariable("eventId") Long eventId) {
        log.info("Received GET request to get requests of event with ID={} of user with ID={}.",
                eventId, userId);
        return privateEventsService.getRequestsOfUserEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(@PathVariable("userId") Long userId,
                                                              @PathVariable("eventId") Long eventId,
                                                              @RequestBody @Valid EventRequestStatusUpdateRequest dto) {
        log.info("Received PATCH request to update request status of event with ID={} of user with ID={}.",
                eventId, userId);
        return privateEventsService.updateRequestStatus(userId, eventId, dto);
    }
}
