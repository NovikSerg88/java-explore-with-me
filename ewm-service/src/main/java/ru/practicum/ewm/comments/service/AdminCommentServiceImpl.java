package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;
import ru.practicum.ewm.comments.mapper.CommentMapper;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.repository.CommentsRepository;
import ru.practicum.ewm.error.NotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;


    @Override
    public CommentDto updateCommentByAdmin(NewCommentDto update, Long commentId) {
        Comment toUpdate = commentsRepository.findById(commentId).orElseThrow(() -> new NotFoundException(
                String.format("Comment with ID = %d not found.", commentId)));
        toUpdate.setText(update.getText());
        toUpdate.setUpdatedAt(LocalDateTime.now());
        return commentMapper.mapToDto(commentsRepository.save(toUpdate));
    }

    @Override
    public void deleteCommentByAdmin(Long commentId) {
        commentsRepository.findById(commentId).orElseThrow(() -> new NotFoundException(
                String.format("Comment with ID = %d not found.", commentId)));
        commentsRepository.deleteById(commentId);
    }
}

