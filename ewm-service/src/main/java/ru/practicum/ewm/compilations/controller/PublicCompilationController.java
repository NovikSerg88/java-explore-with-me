package ru.practicum.ewm.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.service.PublicCompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    @GetMapping("/compilations")
    List<CompilationDto> getCompilations(@RequestParam(value = "pinned", required = false) boolean pinned,
                                         @Valid @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
                                         @Valid @Positive @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Received GET request to get compilations. From: {}, size: {}", from, size);
        return publicCompilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    CompilationDto getCompilationById(@PathVariable("compId") Long compId) {
        log.info("Received GET request to get compilation with ID={}", compId);
        return publicCompilationService.getCompilationById(compId);
    }
}
