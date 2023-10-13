package ru.practicum.ewm.util;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.error.ValidationException;

import java.time.LocalDateTime;

@Component
public class DateValidation {

    public void checkEventDate(LocalDateTime eventDate) {
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Field: eventDate. Error: the date and time for which the event is scheduled" +
                    " cannot be earlier than two hours from the current moment. Value: " + eventDate);
        }
    }
}
