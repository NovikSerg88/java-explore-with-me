package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;
import ru.practicum.util.DateTimeValidation;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;
    private final DateTimeValidation validator;


    @Override
    @Transactional
    public void saveStat(EndpointHitDto endpointHitDto) {
        statsRepository.save(statsMapper.mapToEntity(endpointHitDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        validator.validateDate(start, end);
        if (unique) {
            return statsRepository.findViewStatsUnique(start, end, uris);
        } else {
            return statsRepository.findViewStats(start, end, uris);
        }
    }
}
