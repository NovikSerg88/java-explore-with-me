package ru.practicum.util;

import org.junit.jupiter.api.Test;
import ru.practicum.exception.DateTimeValidationException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationTest {

    DateTimeValidation dateTimeValidation = new DateTimeValidation();

    @Test
    public void throwIfStartIsAfterEndTest() {
        LocalDateTime invalidStart = LocalDateTime.now().plusMinutes(10);
        LocalDateTime end = LocalDateTime.now();

        assertThrows(DateTimeValidationException.class, () -> {
            dateTimeValidation.validateDate(invalidStart, end);
        });
    }
}
