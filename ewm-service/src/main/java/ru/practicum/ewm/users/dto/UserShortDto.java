package ru.practicum.ewm.users.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UserShortDto {
    private final Long id;
    private final String name;
}
