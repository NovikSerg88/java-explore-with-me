package ru.practicum.ewm.comments.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public final class NewCommentDto {
    @NotBlank
    @Size(min = 1, max = 5000)
    private final String text;

    @JsonCreator
    public NewCommentDto(@JsonProperty("text") String text) {
        this.text = text;
    }
}
