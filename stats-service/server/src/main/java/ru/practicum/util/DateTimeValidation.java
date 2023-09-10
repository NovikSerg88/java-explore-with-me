package ru.practicum.util;

import org.springframework.stereotype.Component;
import ru.practicum.exception.StatsServiceException;

import java.time.LocalDateTime;

@Component
public class DateTimeValidation {

    public void validateDate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(LocalDateTime.now()) || start.isAfter(end)) {
            String msg = String.format("Invalid date parameters: %s, %s",
                    start.format(Constants.FORMATTER),
                    end.format(Constants.FORMATTER));
            throw new StatsServiceException(msg);
        }
    }
}
