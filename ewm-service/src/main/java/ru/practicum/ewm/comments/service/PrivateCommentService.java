package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;

public interface PrivateCommentService {

    CommentDto addComment(NewCommentDto dto, Long eventId, Long userId);

    CommentDto updateComment(NewCommentDto update, Long eventId, Long commentId, Long userId);

    void deleteComment(Long commentId, Long userId);
}
