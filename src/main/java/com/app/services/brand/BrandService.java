package com.app.services.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.brand.BrandDTO;
import com.app.entities.Brand;
import com.app.services.base.BaseService;

public interface BrandService extends BaseService<Brand, Long> {

  BrandDTO create(BrandDTO brandto) throws Exception;

  BrandDTO findByID(Long id) throws Exception;

  Page<BrandDTO> findAll(Pageable pageable) throws Exception;

}
