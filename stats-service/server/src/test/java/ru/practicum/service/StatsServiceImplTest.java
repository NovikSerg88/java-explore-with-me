package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsRepository;
import ru.practicum.util.DateTimeValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.*;
import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@ExtendWith(MockitoExtension.class)
public class StatsServiceImplTest {

    @Mock
    StatsRepository statsRepository;

    @Mock
    StatsMapper statsMapper;

    @Mock
    DateTimeValidation validation;

    @InjectMocks
    StatsServiceImpl statsService;

    private final LocalDateTime startTimeStamp = LocalDateTime.parse("2020-05-05 00:00:00",
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    private final LocalDateTime endTimeStamp = LocalDateTime.parse("2035-05-05 00:00:00",
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    private final EndpointHitDto endpointHitDto = EndpointHitDto.builder()
            .app("ewm-main-service")
            .uri("/events/1")
            .ip("192.168.1.1")
            .timestamp("2020-05-05 00:00:00")
            .build();

    private final EndpointHit endpointHit = EndpointHit.builder()
            .app("ewm-main-service")
            .uri("/events/1")
            .ip("192.168.1.1")
            .timestamp(startTimeStamp)
            .build();

    private final ViewStatsDto viewStatsDto = ViewStatsDto.builder()
            .app("ewm-main-service")
            .uri("/events/1")
            .hits(1L)
            .build();

    private final List<ViewStatsDto> stats = List.of(viewStatsDto);

    private final List<String> uris = List.of("/events/1", "/events/2");

    @Test
    void saveStatTest() {
        when(statsMapper.mapToEntity(endpointHitDto)).thenReturn(endpointHit);

        statsService.saveStat(endpointHitDto);

        verify(statsMapper, times(1)).mapToEntity(endpointHitDto);
        verify(statsRepository, times(1)).save(endpointHit);
    }

    @Test
    void getUniqueStatsTest() {
        doNothing().when(validation).validateDate(any(LocalDateTime.class), any(LocalDateTime.class));
        when(statsRepository.findViewStatsUnique(startTimeStamp, endTimeStamp, uris)).thenReturn(stats);

        statsService.getStats(startTimeStamp, endTimeStamp, uris, true);

        verify(statsRepository, times(1)).findViewStatsUnique(startTimeStamp, endTimeStamp, uris);
    }

    @Test
    void getStatsTest() {
        doNothing().when(validation).validateDate(any(LocalDateTime.class), any(LocalDateTime.class));
        when(statsRepository.findViewStats(startTimeStamp, endTimeStamp, uris)).thenReturn(stats);

        statsService.getStats(startTimeStamp, endTimeStamp, uris, false);

        verify(statsRepository, times(1)).findViewStats(startTimeStamp, endTimeStamp, uris);
    }
}
