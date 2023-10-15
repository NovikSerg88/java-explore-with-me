package ru.practicum.ewm.comments.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.events.dto.EventShortDto;
import ru.practicum.ewm.users.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Getter
@Builder
public final class CommentDto {
    private final Long id;
    private final String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime updatedAt;
    private final UserShortDto author;
    private final EventShortDto event;

    @JsonCreator
    public CommentDto(@JsonProperty("id") Long id,
                      @JsonProperty("text") String text,
                      @JsonProperty("createdAt") LocalDateTime createdAt,
                      @JsonProperty("updatedAt") LocalDateTime updatedAt,
                      @JsonProperty("author") UserShortDto author,
                      @JsonProperty("event") EventShortDto event) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
        this.event = event;
    }
}
