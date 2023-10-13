package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.mapper.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoriesRepository;
import ru.practicum.ewm.error.DataIntegrityException;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.RequestValidationException;
import ru.practicum.ewm.events.repository.EventsRepository;

@Service
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final EventsRepository eventsRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        try {
            Category category = categoryMapper.toCategory(newCategoryDto);
            return categoryMapper.toDto(categoriesRepository.saveAndFlush(category));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(e.getMessage());
        }
    }

    @Override
    public CategoryDto updateCategory(Long catId, NewCategoryDto newCategoryDto) {
        Category category = categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with ID = %s not found." + catId)));
        try {
            category.setName(newCategoryDto.getName());
            return categoryMapper.toDto(categoriesRepository.saveAndFlush(category));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(e.getMessage());
        }
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = categoriesRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Category with ID = %s not found", catId)));
        boolean isExist = eventsRepository.existsByCategoryId(catId);
        if (isExist) {
            throw new RequestValidationException(String.format("Category with ID = %s isn't empty", catId));
        } else {
            categoriesRepository.delete(category);
        }
    }
}
