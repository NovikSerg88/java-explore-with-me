package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> getCommentsOfEvent(Long eventId, int from, int size);
}
