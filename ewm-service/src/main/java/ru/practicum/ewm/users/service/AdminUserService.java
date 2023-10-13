package ru.practicum.ewm.users.service;

import ru.practicum.ewm.users.dto.NewUserRequest;
import ru.practicum.ewm.users.dto.UserDto;

import java.util.List;

public interface AdminUserService {

    UserDto createUser(NewUserRequest newUserRequest);

    List<UserDto> getAllUsers(List<Long> ids, int from, int size);

    void deleteUser(Long userId);
}
