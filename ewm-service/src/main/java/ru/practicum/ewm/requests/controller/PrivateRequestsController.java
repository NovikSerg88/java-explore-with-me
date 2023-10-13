package ru.practicum.ewm.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.service.PrivateRequestsService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrivateRequestsController {

    private final PrivateRequestsService privateRequestsService;

    @GetMapping("/users/{userId}/requests")
    List<ParticipationRequestDto> getRequests(@PathVariable("userId") Long userId) {
        log.info("Received GET request to get all participation requests of user with ID={}", userId);
        return privateRequestsService.getRequests(userId);
    }

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable("userId") Long userId,
                                                 @RequestParam(value = "eventId") Long eventId) {
        log.info("Received POST request to create a participation request for event with ID={} by user with ID={}",
                eventId, userId);
        return privateRequestsService.createRequest(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
                                                 @PathVariable("requestId") Long requestId) {
        log.info("Received PATCH request to cancel participation request with ID={}, by user with ID={}",
                requestId, userId);
        return privateRequestsService.cancelRequest(userId, requestId);
    }
}
