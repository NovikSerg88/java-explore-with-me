package ru.practicum.ewm.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.users.dto.NewUserRequest;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.service.AdminUserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Receiving POST request to create user: {}", newUserRequest);
        return adminUserService.createUser(newUserRequest);
    }

    @GetMapping("/admin/users")
    List<UserDto> getAllUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                              @RequestParam(value = "from", defaultValue = "0") int from,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Receiving GET request to get all users");
        return adminUserService.getAllUsers(ids, from, size);
    }


    @DeleteMapping("/admin/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId) {
        log.info("Receiving DELETE request to delete user with ID = {}", userId);
        adminUserService.deleteUser(userId);
    }
}
