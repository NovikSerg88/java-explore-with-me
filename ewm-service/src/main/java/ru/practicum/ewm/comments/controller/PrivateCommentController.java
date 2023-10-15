package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;
import ru.practicum.ewm.comments.service.PrivateCommentService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrivateCommentController {

    private final PrivateCommentService privateCommentService;

    @PostMapping("/events/{eventId}/comments/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestBody @Valid NewCommentDto dto,
                                 @PathVariable("eventId") Long eventId,
                                 @PathVariable("userId") Long userId) {
        log.info("Receiving POST request to create new comment {} from user with ID = {} to event with ID = {}",
                dto, userId, eventId);
        return privateCommentService.addComment(dto, eventId, userId);
    }

    @PatchMapping("/events/{eventId}/comments/{commentId}")
    public CommentDto updateComment(@RequestBody @Valid NewCommentDto update,
                                    @PathVariable("eventId") Long eventId,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestParam Long userId) {
        log.info("Receiving PATCH request to update comment {} from user with ID = {} to event with ID = {}",
                update, userId, eventId);
        return privateCommentService.updateComment(update, eventId, commentId, userId);
    }

    @DeleteMapping("/events/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              @RequestParam("userId") Long userId) {
        log.info("Receiving DELETE request to delete comment from user with ID = {} to event with ID = {}",
                userId, commentId);
        privateCommentService.deleteComment(commentId, userId);
    }
}
