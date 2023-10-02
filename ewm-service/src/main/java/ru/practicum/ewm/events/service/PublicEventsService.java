package ru.practicum.ewm.events.service;

import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventsService {

    List<EventShortDto> getAllEvents(String text,
                                     List<Long> categories,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     Boolean onlyAvailable,
                                     String sort,
                                     int from,
                                     int size,
                                     HttpServletRequest request);

    EventFullDto getEvent(Long id, HttpServletRequest request);
}
