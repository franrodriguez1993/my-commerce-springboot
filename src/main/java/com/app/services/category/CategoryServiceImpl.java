package com.app.services.category;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.dto.category.CategoryDTO;
import com.app.dto.category.CategoryMapper;
import com.app.entities.Category;
import com.app.repositories.BaseRepository;
import com.app.repositories.CategoryRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public CategoryServiceImpl(BaseRepository<Category, Long> baseRepository) {
    super(baseRepository);
  }

  @Override
  public CategoryDTO save(CategoryDTO categorydto) throws Exception {
    try {
      Optional<Category> category = categoryRepository.findByName(categorydto.getName());

      if (category.isPresent()) {
        throw new Exception("CATEGORY_ALREADY_EXISTS");
      }

      Category newCategory = categoryRepository.save(CategoryMapper.INSTANCE.toCategory(categorydto));

      return CategoryMapper.INSTANCE.toCategoryDTO(newCategory);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public CategoryDTO findByID(Long id) throws Exception {
    try {

      Optional<Category> category = categoryRepository.findById(id);

      if (!category.isPresent()) {
        throw new Exception("CATEGORY_NOT_FOUND");
      }

      return CategoryMapper.INSTANCE.toCategoryDTO(category.get());

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public Page<CategoryDTO> listAll(Pageable pageable) throws Exception {
    try {

      Page<Category> categories = categoryRepository.findAll(pageable);

      return CategoryMapper.INSTANCE.toCategoriesDTO(categories);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
