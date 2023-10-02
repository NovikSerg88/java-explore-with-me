package ru.practicum.ewm.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.ewm.error.ServiceException;
import ru.practicum.ewm.requests.dto.Status;
import ru.practicum.ewm.requests.model.ParticipationRequest;
import ru.practicum.ewm.requests.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsClient statsClient;
    private final ObjectMapper objectMapper;
    private final RequestRepository requestRepository;

    @Value("${ewm.service.name}")
    private String serviceName;


    @Override
    public List<ViewStatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        try {
            ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);
            Object body = response.getBody();
            String bodyStr = objectMapper.writeValueAsString(body);
            List<ViewStatsDto> statistics = objectMapper.readValue(bodyStr, new TypeReference<>() {
            });
            return statistics;
        } catch (JsonProcessingException e) {
            throw new ServiceException("Failed to parse response from Statistics Client");
        }
    }

    @Override
    public Map<Long, Long> getEventsViews(List<Long> eventIds,
                                          LocalDateTime start,
                                          LocalDateTime end,
                                          boolean unique) {
        List<String> uris = eventIds.stream()
                .map(id -> "/events/" + id)
                .collect(Collectors.toList());
        List<ViewStatsDto> statistics = getStatistics(start, end, uris, unique);
        return statistics.stream()
                .collect(Collectors.toMap(
                        viewStatsDto -> eventIds.get(uris.indexOf(viewStatsDto.getUri())),
                        ViewStatsDto::getHits
                ));
    }

    @Override
    public Map<Long, Long> getConfirmedCount(List<Long> eventsIds) {
        List<ParticipationRequest> confirmedRequests = requestRepository
                .findAllByStatusAndEventIdIn(Status.CONFIRMED, eventsIds);

        return confirmedRequests.stream()
                .collect(Collectors.groupingBy(request -> request.getEvent().getId()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()));
    }

    @Override
    public Long getConfirmedCountForEvent(Long eventId) {
        Map<Long, Long> confirmedCount = getConfirmedCount(List.of(eventId));
        return confirmedCount.getOrDefault(eventId, 0L);
    }

    @Override
    public Long getEventViews(Long eventId, LocalDateTime start, LocalDateTime end, boolean unique) {
        Map<Long, Long> eventsViews = getEventsViews(List.of(eventId), start, end, unique);
        return eventsViews.getOrDefault(eventId, 0L);
    }

    @Override
    public void saveStats(String app, String uri, String ip, LocalDateTime timestamp) {
        statsClient.save(app, uri, ip, timestamp);
    }
}


