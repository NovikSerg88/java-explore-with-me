package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.service.PublicCommentService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PublicCommentController {

    private final PublicCommentService publicCommentService;

    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getCommentsOfEvent(@PathVariable("eventId") Long eventId,
                                               @RequestParam(value = "from", defaultValue = "0") int from,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Receiving GET request to get all comments of Event with ID = {}", eventId);
        return publicCommentService.getCommentsOfEvent(eventId, from, size);
    }
}
