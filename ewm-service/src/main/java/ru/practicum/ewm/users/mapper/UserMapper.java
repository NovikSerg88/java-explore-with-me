package ru.practicum.ewm.users.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.users.dto.NewUserRequest;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.dto.UserShortDto;
import ru.practicum.ewm.users.model.User;

@Component
public class UserMapper {

    public User toUser(NewUserRequest dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public UserShortDto toShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
