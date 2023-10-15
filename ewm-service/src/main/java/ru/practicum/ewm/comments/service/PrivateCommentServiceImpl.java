package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;
import ru.practicum.ewm.comments.mapper.CommentMapper;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.repository.CommentsRepository;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.RequestValidationException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.repository.EventsRepository;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addComment(NewCommentDto dto, Long eventId, Long userId) {
        User author = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                String.format("User with ID = %d not found.", userId)));

        Event event = eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event with ID = %d not found.", eventId)));

        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new ValidationException("Only published events can been commented");
        }

        Comment comment = commentMapper.mapToComment(dto, author, event);
        return commentMapper.mapToDto(commentsRepository.saveAndFlush(comment));
    }

    @Override
    public CommentDto updateComment(NewCommentDto update, Long eventId, Long commentId, Long userId) {
        eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event with ID = %d not found.", eventId)));
        Comment toUpdate = commentsRepository.findById(commentId).orElseThrow(() -> new NotFoundException(
                String.format("Comment with ID = %d not found.", commentId)));
        if (!userId.equals(toUpdate.getAuthor().getId())) {
            throw new RequestValidationException("Only author of comment or admin can update comment");
        }
        toUpdate.setText(update.getText());
        toUpdate.setUpdatedAt(LocalDateTime.now());
        return commentMapper.mapToDto(commentsRepository.save(toUpdate));
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(() -> new NotFoundException(
                String.format("Comment with ID = %d not found.", commentId)));
        if (!userId.equals(comment.getAuthor().getId())) {
            throw new RequestValidationException("Only author of comment or admin can delete comment");
        }
        commentsRepository.deleteById(commentId);
    }
}
