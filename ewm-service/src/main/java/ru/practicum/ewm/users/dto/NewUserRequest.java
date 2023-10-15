package ru.practicum.ewm.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public final class NewUserRequest {
    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    private final String email;
    @NotBlank
    @Size(min = 2, max = 250)
    private final String name;

    @JsonCreator
    public NewUserRequest(@JsonProperty("email") String email,
                          @JsonProperty("name") String name) {
        this.email = email;
        this.name = name;
    }
}
