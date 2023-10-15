package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.NewCommentDto;
import ru.practicum.ewm.comments.service.AdminCommentService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @PatchMapping("/admin/comments")
    public CommentDto updateCommentByAdmin(@RequestBody @Valid NewCommentDto update,
                                           @RequestParam Long commentId) {
        log.info("Receiving PATCH request to update comment {} by admin", update);
        return adminCommentService.updateCommentByAdmin(update, commentId);
    }

    @DeleteMapping("/admin/comments")
    public void deleteCommentByAdmin(@RequestParam Long commentId) {
        log.info("Receiving DELETE request to delete comment with ID = {} by admin", commentId);
        adminCommentService.deleteCommentByAdmin(commentId);
    }
}
