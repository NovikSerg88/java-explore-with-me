package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.mapper.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoriesRepository;
import ru.practicum.ewm.error.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicCategoriesServiceImpl implements PublicCategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from, size);
        Page<Category> categoryPage = categoriesRepository.findAll(pageRequest);
        return categoryPage
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found with ID = " + catId));
        return categoryMapper.toDto(category);
    }
}
