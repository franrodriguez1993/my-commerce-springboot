package com.app.services.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.category.CategoryDTO;
import com.app.entities.Category;
import com.app.services.base.BaseService;

public interface CategoryService extends BaseService<Category, Long> {

  CategoryDTO save(CategoryDTO category) throws Exception;

  CategoryDTO findByID(Long id) throws Exception;

  Page<CategoryDTO> listAll(Pageable pageable) throws Exception;
}
