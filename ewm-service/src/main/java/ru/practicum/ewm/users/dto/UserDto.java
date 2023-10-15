package ru.practicum.ewm.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class UserDto {
    private final Long id;
    private final String email;
    private final String name;

    @JsonCreator
    public UserDto(@JsonProperty("id") Long id,
                   @JsonProperty("email") String email,
                   @JsonProperty("name") String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
