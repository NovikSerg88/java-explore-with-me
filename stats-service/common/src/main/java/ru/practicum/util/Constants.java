package ru.practicum.util;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class Constants {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
}

