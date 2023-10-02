package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}
