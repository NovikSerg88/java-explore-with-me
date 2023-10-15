package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;

public interface AdminCommentService {

    CommentDto updateCommentByAdmin(NewCommentDto update, Long commentId);

    void deleteCommentByAdmin(Long commentId);
}
