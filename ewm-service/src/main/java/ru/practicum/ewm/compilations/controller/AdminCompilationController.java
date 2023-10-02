package ru.practicum.ewm.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilations.service.AdminCompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Received POST request to create new compilation: {}", newCompilationDto);
        return adminCompilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        log.info("Received request to DELETE compilation with ID={}", compId);
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto updateCompilation(@PathVariable("compId") Long compId,
                                            @RequestBody @Valid UpdateCompilationRequest dto) {
        log.info("Received PATCH request to update compilation with ID={}. To be updated to: {}", compId, dto);
        return adminCompilationService.updateCompilation(compId, dto);
    }
}
