package ru.practicum.ewm.users.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UserDto {
    private final Long id;
    private final String email;
    private final String name;
}
