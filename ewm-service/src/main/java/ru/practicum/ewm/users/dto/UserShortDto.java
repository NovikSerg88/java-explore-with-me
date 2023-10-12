package ru.practicum.ewm.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class UserShortDto {
    private final Long id;
    private final String name;

    @JsonCreator
    public UserShortDto(@JsonProperty("id") Long id,
                        @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
