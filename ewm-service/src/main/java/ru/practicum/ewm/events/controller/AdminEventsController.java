package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.events.service.AdminEventsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminEventsController {

    private final AdminEventsService adminEventsService;

    @GetMapping("/admin/events")
    public List<EventFullDto> getAllEvents(@RequestParam(value = "users", required = false) List<Long> users,
                                           @RequestParam(value = "states", required = false) List<EventState> states,
                                           @RequestParam(value = "categories", required = false) List<Long> categories,
                                           @RequestParam(value = "rangeStart", required = false)
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                           @RequestParam(value = "rangeEnd", required = false)
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                           @RequestParam(value = "from", defaultValue = "0") int from,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Received GET request to get events from {} to {} with users {}, states {}, " +
                        "categories {} rangeStart {}, rangeEnd {}", from, size, users, states,
                categories, rangeStart, rangeEnd);
        return adminEventsService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable("eventId") Long eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest update) {
        log.info("Received admin PATCH request to update event with ID={} to new event={}",
                eventId, update);
        return adminEventsService.updateEvent(eventId, update);
    }
}
