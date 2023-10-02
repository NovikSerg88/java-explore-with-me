package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Receiving POST request to save stat hit: {}", endpointHitDto);
        statsService.saveStat(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam("start") String start,
                                       @RequestParam("end") String end,
                                       @RequestParam(value = "uris", required = false) List<String> uris,
                                       @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info("Received request to GET statistics from start: {} to end: {} period", start, end);
        return statsService.getStats(start, end, uris, unique);
    }
}
