package ru.practicum.ewm.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_FORMAT;

@Data
@Builder
public class ApiError {
    private String message;
    private String reason;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
}
