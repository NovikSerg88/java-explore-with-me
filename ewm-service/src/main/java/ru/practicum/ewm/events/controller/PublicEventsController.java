package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventShortDto;
import ru.practicum.ewm.events.service.PublicEventsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicEventsController {

    private final PublicEventsService publicEventsService;

    @GetMapping("/events")
    public List<EventShortDto> getAllEvents(@RequestParam(value = "text", required = false) String text,
                                            @RequestParam(value = "categories", required = false) List<Long> categories,
                                            @RequestParam(value = "paid", required = false) Boolean paid,
                                            @RequestParam(value = "rangeStart", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(value = "rangeEnd", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(value = "onlyAvailable", required = false) boolean onlyAvailable,
                                            @RequestParam(value = "sort", required = false) String sort,
                                            @RequestParam(value = "from", defaultValue = "0") int from,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            HttpServletRequest request) {
        log.info("Received GET request to пуе events with params: text={}, categories={}, " +
                        "paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}", text,
                categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return publicEventsService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size, request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable("id") Long id, HttpServletRequest request) {
        log.info("Received GET request to get an event with ID={}.", id);
        return publicEventsService.getEvent(id, request);
    }
}
