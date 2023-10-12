package ru.practicum.ewm.events.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.categories.mapper.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventShortDto;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.dto.NewEventDto;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.service.StatsService;
import ru.practicum.ewm.users.mapper.UserMapper;
import ru.practicum.ewm.users.model.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final StatsService statsService;
    private final CategoryMapper categoryMapper;
    private final LocationMapper locationMapper;
    private final UserMapper userMapper;

    public Event mapToEvent(NewEventDto dto, Category category, User user) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(category)
                .description(dto.getDescription())
                .createdOn(LocalDateTime.now())
                .eventDate(dto.getEventDate())
                .initiator(user)
                .location(locationMapper.mapToLocation(dto.getLocation()))
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.getRequestModeration())
                .title(dto.getTitle())
                .state(EventState.PENDING)
                .build();
    }

    public EventFullDto mapToEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(statsService.getConfirmedCountForEvent(event.getId()))
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .location(locationMapper.mapToDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(statsService.getEventViews(
                        event.getId(),
                        event.getCreatedOn(),
                        LocalDateTime.now(),
                        true
                ))
                .build();
    }

    public EventShortDto mapToEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(statsService.getConfirmedCountForEvent(event.getId()))
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(statsService.getEventViews(
                        event.getId(),
                        event.getCreatedOn(),
                        LocalDateTime.now(),
                        true))
                .build();
    }
}
