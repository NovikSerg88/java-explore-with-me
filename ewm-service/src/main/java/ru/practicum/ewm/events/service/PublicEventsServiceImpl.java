package ru.practicum.ewm.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventShortDto;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.mapper.EventMapper;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventsRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventsServiceImpl implements PublicEventsService {

    private final StatsService statsService;
    private final EventsRepository eventsRepository;
    private final EventMapper eventMapper;

    @Value("${ewm.service.name}")
    private String serviceName;

    @Override
    public List<EventShortDto> getAllEvents(String text,
                                            List<Long> categories,
                                            Boolean paid,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            Boolean onlyAvailable,
                                            String sort,
                                            int from,
                                            int size,
                                            HttpServletRequest request) {
        Pageable page = PageRequest.of(from / size, size);
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd != null && rangeEnd.isBefore(rangeStart)) {
            throw new ValidationException("RangeStart cannot be later than rangeEnd");
        }

        List<Event> events = eventsRepository.findEventsForPublic(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, page);
        List<EventShortDto> dtos = events.stream()
                .map(eventMapper::mapToEventShortDto)
                .collect(Collectors.toList());


        if (sort != null && sort.equals("EVENT_DATE")) {
            dtos = dtos.stream()
                    .sorted(Comparator.comparing(EventShortDto::getEventDate))
                    .collect(Collectors.toList());
        }
        if (sort != null && sort.equals("VIEWS")) {
            dtos = dtos.stream()
                    .sorted(Comparator.comparing(EventShortDto::getViews))
                    .collect(Collectors.toList());
        }
        statsService.saveStats(serviceName, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());

        return dtos;
    }

    @Override
    public EventFullDto getEvent(Long id, HttpServletRequest request) {
        Event event = eventsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with ID={} not found", id)));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException(String.format("The event by ID = {} has not been published yet", id));
        }
        statsService.saveStats(serviceName, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        EventFullDto eventFullDto = eventMapper.mapToEventFullDto(event);
        return eventFullDto;
    }
}
