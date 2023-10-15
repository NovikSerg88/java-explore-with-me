package ru.practicum.ewm.comments.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.events.mapper.EventMapper;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.users.mapper.UserMapper;
import ru.practicum.ewm.users.model.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    public Comment mapToComment(NewCommentDto dto, User user, Event event) {
        return Comment.builder()
                .text(dto.getText())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .author(user)
                .event(event)
                .build();
    }

    public CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .author(userMapper.toShortDto(comment.getAuthor()))
                .event(eventMapper.mapToEventShortDto(comment.getEvent()))
                .build();
    }
}
