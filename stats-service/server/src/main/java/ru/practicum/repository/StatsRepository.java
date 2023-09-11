package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select new ru.practicum.dto.ViewStatsDto(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit e " +
            "where e.timestamp between ?1 and ?2 " +
            "and (e.uri in (?3) or (?3) is null) " +
            "group by e.app, e.uri " +
            "order by count(distinct e.ip) desc")
    List<ViewStatsDto> findViewStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.dto.ViewStatsDto(e.app, e.uri, count(e.ip)) " +
            "from EndpointHit e " +
            "where e.timestamp between ?1 and ?2 " +
            "and (e.uri in (?3) or (?3) is null) " +
            "group by e.app, e.uri " +
            "order by count(e.ip) desc")
    List<ViewStatsDto> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
