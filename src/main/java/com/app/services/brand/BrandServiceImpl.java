package com.app.services.brand;

import org.springframework.stereotype.Service;

import com.app.entities.Brand;
import com.app.repositories.BaseRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, Long> implements BrandService {

  public BrandServiceImpl(BaseRepository<Brand, Long> baseRepository) {
    super(baseRepository);
  }
}
