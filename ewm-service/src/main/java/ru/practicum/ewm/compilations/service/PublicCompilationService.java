package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> getCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compId);
}
