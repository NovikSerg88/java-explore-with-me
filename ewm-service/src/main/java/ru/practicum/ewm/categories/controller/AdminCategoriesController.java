package ru.practicum.ewm.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.service.AdminCategoriesService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCategoriesController {

    private final AdminCategoriesService adminCategoriesService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Receiving POST request to create new category {}", newCategoryDto);
        return adminCategoriesService.createCategory(newCategoryDto);
    }

    @PatchMapping("/admin/categories/{catId}")
    public CategoryDto updateCategory(@PathVariable("catId") Long catId,
                                      @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Receiving PATH request to update category with ID = {}", catId);
        return adminCategoriesService.updateCategory(catId, newCategoryDto);
    }

    @DeleteMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") Long catId) {
        log.info("Receiving DELETE request to delete category with ID = {}", catId);
        adminCategoriesService.deleteCategory(catId);
    }
}
