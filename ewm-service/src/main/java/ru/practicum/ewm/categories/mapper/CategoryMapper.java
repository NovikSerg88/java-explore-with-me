package ru.practicum.ewm.categories.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.NewCategoryDto;
import ru.practicum.ewm.categories.model.Category;

@Component
public class CategoryMapper {

    public Category toCategory(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
