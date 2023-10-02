package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;
import ru.practicum.util.DateTimeValidation;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.FORMATTER;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;
    private final DateTimeValidation validator;


    @Override
    public void saveStat(EndpointHitDto endpointHitDto) {
        statsRepository.save(statsMapper.mapToEntity(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getStats(String startString, String endString, List<String> uris, boolean unique) {
        LocalDateTime start = LocalDateTime.parse(URLDecoder.decode(startString, StandardCharsets.UTF_8), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(URLDecoder.decode(endString, StandardCharsets.UTF_8), FORMATTER);
        validator.validateDate(start, end);
        if (unique) {
            return statsRepository.findViewStatsUnique(start, end, uris);
        } else {
            return statsRepository.findViewStats(start, end, uris);
        }
    }
}

