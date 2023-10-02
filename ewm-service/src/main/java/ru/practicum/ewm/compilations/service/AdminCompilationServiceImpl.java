package ru.practicum.ewm.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilations.mapper.CompilationMapper;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.compilations.repository.CompilationRepository;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.RequestValidationException;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventsRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventsRepository eventsRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {
        List<Event> events = getCompilationEvents(dto.getEvents());
        Compilation compilation = compilationMapper.mapToEntity(dto, events);
        Compilation saved = compilationRepository.save(compilation);
        if (events != null && !events.isEmpty()) {
            events.forEach(event -> event.setCompilation(compilation));
        }
        return compilationMapper.mapToDto(saved);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.findById(compId)
                .ifPresentOrElse(
                        user -> compilationRepository.deleteById(compId),
                        () -> {
                            throw new NotFoundException(String.format("Compilation with ID = {} not found", compId));
                        }
                );
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest update) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("Compilation with ID = {} not found", compId));
                });
        if (update.getPinned() != null) {
            compilation.setPinned(update.getPinned());
        }
        if (update.getTitle() != null) {
            compilation.setTitle(update.getTitle());

        }
        List<Event> events = update.getEvents() == null ? null : getCompilationEvents(update.getEvents());
        compilation.setEvents(events);
        return compilationMapper.mapToDto(compilationRepository.save(compilation));
    }

    private List<Event> getCompilationEvents(List<Long> eventIds) {
        List<Event> events = Collections.emptyList();
        if (null != eventIds && !eventIds.isEmpty()) {
            checkAllEventsExist(eventIds);
            events = eventsRepository.findAllByIdIn(List.copyOf(eventIds));
        }
        return events;
    }

    private void checkAllEventsExist(List<Long> eventIds) {
        Long eventsCount = eventsRepository.countAllByIdIn(List.copyOf(eventIds));
        if (eventsCount != eventIds.size()) {
            throw new RequestValidationException("Events don`t exist");
        }
    }
}
