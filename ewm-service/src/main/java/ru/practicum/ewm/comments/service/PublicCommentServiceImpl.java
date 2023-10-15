package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.mapper.CommentMapper;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.repository.CommentsRepository;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.events.repository.EventsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentsRepository commentsRepository;
    private final EventsRepository eventsRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getCommentsOfEvent(Long eventId, int from, int size) {
        Pageable page = PageRequest.of(from / size, size);
        eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event with ID = %d not found.", eventId)));
        Page<Comment> comments = commentsRepository.findAllByEventId(eventId, page);
        return comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
