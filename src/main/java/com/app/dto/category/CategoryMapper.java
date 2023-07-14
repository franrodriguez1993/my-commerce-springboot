package com.app.dto.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.Category;

@Mapper
public interface CategoryMapper {

  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  Category toCategory(CategoryDTO categorydto);

  CategoryDTO toCategoryDTO(Category category);

  default Page<CategoryDTO> toCategoriesDTO(Page<Category> categories) {
    return categories.map(this::toCategoryDTO);
  }
}
