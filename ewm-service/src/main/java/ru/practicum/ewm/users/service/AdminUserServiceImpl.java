package ru.practicum.ewm.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.error.DataIntegrityException;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.users.dto.NewUserRequest;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.mapper.UserMapper;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        try {
            User user = userMapper.toUser(newUserRequest);
            return userMapper.toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(e.getMessage());
        }
    }

    @Override
    public List<UserDto> getAllUsers(List<Long> ids, int from, int size) {
        Pageable page = PageRequest.of(from, size);
        Page<User> userPage;
        if (ids == null) {
            userPage = userRepository.findAll(page);
        } else {
            userPage = userRepository.findByIdIn(ids, page);
        }
        return userPage
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        user -> userRepository.deleteById(userId),
                        () -> {
                            throw new NotFoundException("User not found with ID: " + userId);
                        }
                );
    }
}
