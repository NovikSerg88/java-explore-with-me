package ru.practicum.ewm.events.service;

import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {

    List<ViewStatsDto> getStatistics(LocalDateTime start,
                                     LocalDateTime end,
                                     List<String> uris,
                                     Boolean unique);

    void saveStats(String app, String uri, String ip, LocalDateTime timestamp);

    Map<Long, Long> getEventsViews(List<Long> eventIds,
                                   LocalDateTime start,
                                   LocalDateTime end,
                                   boolean unique);

    Map<Long, Long> getConfirmedCount(List<Long> eventsIds);

    Long getConfirmedCountForEvent(Long eventId);

    Long getEventViews(Long eventId, LocalDateTime start, LocalDateTime end, boolean unique);

}
