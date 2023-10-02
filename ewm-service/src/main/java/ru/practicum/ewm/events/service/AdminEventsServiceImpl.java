package ru.practicum.ewm.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoriesRepository;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.RequestValidationException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.events.dto.AdminEventStateAction;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.events.mapper.EventMapper;
import ru.practicum.ewm.events.mapper.LocationMapper;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventsServiceImpl implements AdminEventsService {

    private final EventsRepository eventsRepository;
    private final CategoriesRepository categoriesRepository;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;

    @Override
    public List<EventFullDto> getAllEvents(List<Long> users,
                                           List<EventState> states,
                                           List<Long> categories,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           int from,
                                           int size) {
        Pageable page = PageRequest.of(from / size, size);
        List<Event> events = eventsRepository.findEventsForAdmin(users, states, categories, rangeStart, rangeEnd, page);
        return events.stream()
                .map(eventMapper::mapToEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest update) {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with ID={} not found", eventId)));

        if (update.getStateAction() != null) {
            if (update.getStateAction().equals(AdminEventStateAction.PUBLISH_EVENT)) {
                if (!event.getState().equals(EventState.PENDING)) {
                    throw new RequestValidationException(String.format("Event %s has already been published", eventId));
                }
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                if (!event.getState().equals(EventState.PENDING)) {
                    throw new RequestValidationException("Event must be in PENDING status");
                }
                event.setState(EventState.CANCELED);
            }
        }
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
            throw new ValidationException("The start date of the modified event must be" +
                    " no earlier than one hour from the publication date");
        }
        patchUpdateEvent(update, event);
        return eventMapper.mapToEventFullDto(eventsRepository.save(event));
    }

    private void patchUpdateEvent(UpdateEventAdminRequest dto, Event event) {
        if (dto.getAnnotation() != null && !dto.getAnnotation().isBlank()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoriesRepository.findById(dto.getCategory()).orElseThrow(() ->
                    new NotFoundException(String.format("Category %s not found", dto.getCategory())));
            event.setCategory(category);
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getLocation() != null) {
            event.setLocation(locationMapper.mapToLocation(dto.getLocation()));
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            event.setTitle(dto.getTitle());
        }
    }
}
