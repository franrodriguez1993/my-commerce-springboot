package com.app.services.category;

import org.springframework.stereotype.Service;

import com.app.entities.Category;
import com.app.repositories.BaseRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

  public CategoryServiceImpl(BaseRepository<Category, Long> baseRepository) {
    super(baseRepository);
  }

}
