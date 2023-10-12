package ru.practicum.ewm.categories.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public final class NewCategoryDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private final String name;

    @JsonCreator
    public NewCategoryDto(@JsonProperty("name") String name) {
        this.name = name;
    }
}
