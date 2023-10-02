package ru.practicum.ewm.users.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public final class NewUserRequest {
    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    private final String email;
    @NotBlank
    @Size(min = 2, max = 250)
    private final String name;
}
